package com.BrantleyFan.blog.service;

import com.BrantleyFan.blog.vo.Result;
import com.BrantleyFan.blog.vo.params.ArticleParam;
import com.BrantleyFan.blog.vo.params.PageParams;

public interface ArticleService {
    Result listArticle(PageParams pageParams);

    int getArticleCount();

    Result listArchive();

    Result publish(ArticleParam articleParam);

    Result getArticle(int articleId);

    Result searchArticle(String keyword);

    Result clickHit(int articleId);

    Result listHotArticle(PageParams pageParams);

    Result getArticleListByCategory(int categoryId);
}
