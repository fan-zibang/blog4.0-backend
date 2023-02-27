package com.BrantleyFan.blog.controller;

import com.BrantleyFan.blog.common.aop.LogAnnotation;
import com.BrantleyFan.blog.service.ArticleService;
import com.BrantleyFan.blog.vo.Result;
import com.BrantleyFan.blog.vo.params.ArticleParam;
import com.BrantleyFan.blog.vo.params.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping
    // 此接口记录日志
    @LogAnnotation(module="文章",operator="获取文章列表")
    public Result listArticle(@RequestBody PageParams pageParams){
        return articleService.listArticle(pageParams);
    }

    @PostMapping("/hot")
    public Result listHotArticle(@RequestBody PageParams pageParams) {
        return  articleService.listHotArticle(pageParams);
    }

    @GetMapping("/pageCounts")
    public int getArticleCount(){
        return articleService.getArticleCount();
    }

    @PostMapping("/archive")
    public Result listArchive(){
        return  articleService.listArchive();
    }

    @PostMapping("/publish")
    public Result publish(@RequestBody ArticleParam articleParam){
        System.out.println(articleParam);
        return articleService.publish(articleParam);
    }

    @PostMapping("view/{id}")
    public Result getArticle(@PathVariable("id") int articleId){
        return articleService.getArticle(articleId);
    }

    @GetMapping("/clickHit/{id}")
    public Result clickHit(@PathVariable("id") int articleId){
        return articleService.clickHit(articleId);
    }

    @PostMapping("/keyword/{keyword}")
    public Result searchArticle(@PathVariable("keyword") String keyword){
        return articleService.searchArticle(keyword);
    }

    @PostMapping("/category/{id}")
    public Result getArticleListByCategory(@PathVariable("id") int categoryId){
        return articleService.getArticleListByCategory(categoryId);
    }
}
