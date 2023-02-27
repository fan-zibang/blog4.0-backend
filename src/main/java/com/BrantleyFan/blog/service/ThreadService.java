package com.BrantleyFan.blog.service;

import com.BrantleyFan.blog.mapper.ArticleMapper;
import com.BrantleyFan.blog.pojo.Article;

public interface ThreadService {
    void updateArticleViewCount(ArticleMapper articleMapper, Article article);
}
