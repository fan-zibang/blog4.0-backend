package com.BrantleyFan.blog.service.impl;

import com.BrantleyFan.blog.mapper.TagMapper;
import com.BrantleyFan.blog.pojo.Tag;
import com.BrantleyFan.blog.service.TagService;
import com.BrantleyFan.blog.vo.Result;
import com.BrantleyFan.blog.vo.TagVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper tagMapper;

    @Override
    public List<TagVo> findTagByArticleId(int articleId) {
        return tagMapper.findTagByArticleId(articleId);
    }

    @Override
    public Result getTagList() {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        return Result.success(tagMapper.selectList(queryWrapper));
    }

    @Override
    public Result editTag(Tag tagParams) {
        int i = tagMapper.updateById(tagParams);
        if (i > 0){
            return Result.success("标签信息修改成功");
        }
        return null;
    }

    @Override
    public Result deleteTag(int id) {
        int i = tagMapper.deleteById(id);
        if (i > 0){
            return Result.success("标签删除成功");
        }
        return null;
    }
}
