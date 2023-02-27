package com.BrantleyFan.blog.mapper;

import com.BrantleyFan.blog.pojo.Comment;
import com.BrantleyFan.blog.vo.ReplyCommentVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

public interface CommentMapper extends BaseMapper<Comment> {
    IPage<Comment> listCommentPage(Page<?> page);

    List<Comment> selectReplyListByParentId(Integer parentId);
}
