package com.BrantleyFan.blog.mapper;

import com.BrantleyFan.blog.pojo.Tag;
import com.BrantleyFan.blog.vo.TagVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface TagMapper extends BaseMapper<Tag> {

    List<TagVo> findTagByArticleId(int articleId);
}
