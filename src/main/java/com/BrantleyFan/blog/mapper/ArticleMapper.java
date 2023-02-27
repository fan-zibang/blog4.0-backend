package com.BrantleyFan.blog.mapper;

import com.BrantleyFan.blog.pojo.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public interface ArticleMapper extends BaseMapper<Article> {
    IPage<Article> listArticlePage(Page<?> page);

    int getArticleCount();

    IPage<Article> listHotArticlePage(Page<?> page);
}
