package com.BrantleyFan.blog.service.impl;

import com.BrantleyFan.blog.mapper.CommentMapper;
import com.BrantleyFan.blog.pojo.Comment;
import com.BrantleyFan.blog.service.CommentService;
import com.BrantleyFan.blog.vo.CommentVo;
import com.BrantleyFan.blog.vo.ReplyCommentVo;
import com.BrantleyFan.blog.vo.Result;
import com.BrantleyFan.blog.vo.params.CommentParams;
import com.BrantleyFan.blog.vo.params.PageParams;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    /**
     * 获取总留言条数
     */
    @Override
    public Result getCommentCount() {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper();
        Integer count = commentMapper.selectCount(queryWrapper);
        return Result.success(count);
    }

    /**
     * 获得Level1的留言总条数
     */
    public Result getCommentCountByLevelOne() {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getLevel,"1");
        Integer count = commentMapper.selectCount(queryWrapper);
        return Result.success(count);
    }

    /**
     * 获得留言列表
     * @param pageParams
     * @return
     */
    @Override
    public Result getCommentList(PageParams pageParams) {
        Page<Comment> page = new Page<>(pageParams.getPage(), pageParams.getPageSize());
        IPage<Comment> commentIPage = commentMapper.listCommentPage(page);
        List<Comment> commentList = commentIPage.getRecords();
        List<CommentVo> commentVoList = new ArrayList<>();
        for (Comment comment : commentList) {
            CommentVo commentVo = new CommentVo();
            BeanUtils.copyProperties(comment,commentVo);
            commentVo.setCreateTime(new DateTime(comment.getCreateTime()).toString("yyyy-MM-dd HH:mm"));
            commentVo.setCommentVoList(getReplyCommentList(comment.getId()));
            commentVoList.add(commentVo);
        }
        return Result.success(commentVoList);
    }

    /**
     * 获得回复列表
     * @param parentId
     * @return
     */
    private List<ReplyCommentVo> getReplyCommentList(int parentId) {
        List<Comment> commentList = commentMapper.selectReplyListByParentId(parentId);
        List<ReplyCommentVo> replyCommentVoList = new ArrayList<>();
        for (Comment comment : commentList) {
            ReplyCommentVo replyCommentVo = new ReplyCommentVo();
            BeanUtils.copyProperties(comment,replyCommentVo);
            replyCommentVo.setCreateTime(new DateTime(comment.getCreateTime()).toString("yyyy-MM-dd HH:mm"));
            replyCommentVoList.add(replyCommentVo);
        }
        return replyCommentVoList;
    }

    @Override
    public Result publishComment(CommentParams commentParams) {
        Comment comment = new Comment();
        if (StringUtils.isBlank(commentParams.getUsername())){
            return null;
        } else {
            comment.setUsername(commentParams.getUsername());
        }
        comment.setMessage(commentParams.getMessage());
        comment.setUsername(commentParams.getUsername());
        comment.setEmail(commentParams.getEmail());
        comment.setCreateTime(System.currentTimeMillis());
        comment.setLevel(commentParams.getLevel());
        comment.setParentId(commentParams.getParentId());
        comment.setAuthor(commentParams.getAuthor());
        this.commentMapper.insert(comment);
        return Result.success("留言发布成功");
    }

    @Override
    public Result deleteComment(int id) {
        int i = commentMapper.deleteById(id);
        LambdaUpdateWrapper<Comment> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Comment::getParentId,id);
        commentMapper.delete(updateWrapper);
        if (i > 0){
            return Result.success("评论删除成功");
        }
        return null;
    }
}
