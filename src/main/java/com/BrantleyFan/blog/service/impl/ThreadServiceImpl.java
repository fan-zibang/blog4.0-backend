package com.BrantleyFan.blog.service.impl;

import com.BrantleyFan.blog.mapper.ArticleMapper;
import com.BrantleyFan.blog.pojo.Article;
import com.BrantleyFan.blog.service.ThreadService;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class ThreadServiceImpl implements ThreadService {

    @Async("taskExcutor")
    @Override
    public void updateArticleViewCount(ArticleMapper articleMapper, Article article) {
        int viewCounts = article.getViewCounts();
        Article articleUpdate = new Article();
        articleUpdate.setViewCounts(viewCounts + 1);
        LambdaUpdateWrapper<Article> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Article::getId,article.getId());
        // 设置一个 为了多线程安全
        updateWrapper.eq(Article::getViewCounts,viewCounts);
        articleMapper.update(articleUpdate,updateWrapper);

        // 设置睡眠5秒，不会影响主线程的使用
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
