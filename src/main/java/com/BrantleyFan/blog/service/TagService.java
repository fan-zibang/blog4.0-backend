package com.BrantleyFan.blog.service;

import com.BrantleyFan.blog.pojo.Tag;
import com.BrantleyFan.blog.vo.Result;
import com.BrantleyFan.blog.vo.TagVo;

import java.util.List;

public interface TagService {

    List<TagVo> findTagByArticleId(int articleId);

    Result getTagList();

    Result deleteTag(int id);

    Result editTag(Tag tagParams);
}
