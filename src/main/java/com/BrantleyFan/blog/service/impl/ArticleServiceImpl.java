package com.BrantleyFan.blog.service.impl;

import com.BrantleyFan.blog.mapper.ArticleContentMapper;
import com.BrantleyFan.blog.mapper.ArticleMapper;
import com.BrantleyFan.blog.mapper.ArticleTagMapper;
import com.BrantleyFan.blog.pojo.*;
import com.BrantleyFan.blog.service.*;
import com.BrantleyFan.blog.utils.UserThreadLocal;
import com.BrantleyFan.blog.vo.*;
import com.BrantleyFan.blog.vo.params.ArticleParam;
import com.BrantleyFan.blog.vo.params.PageParams;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private TagService tagService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ArticleTagMapper articleTagMapper;
    @Autowired
    private ArticleContentMapper articleContentMapper;
    @Autowired
    private ThreadService threadService;

    /**
     * 获得文章的总数
     */
    @Override
    public int getArticleCount() {
        return articleMapper.getArticleCount();
    }

    /**
     * 分页查询article表
     */
    @Override
    public Result listArticle(PageParams pageParams) {
        Page<Article> page = new Page<>(pageParams.getPage(), pageParams.getPageSize());
        IPage<Article> articleIPage = articleMapper.listArticlePage(page);
        List<Article> records = articleIPage.getRecords();
        List<ArticleVo> articleVoList = copyList(records);
        return Result.success(articleVoList);
    }

    /**
     * 查询热门文章
     */
    @Override
    public Result listHotArticle(PageParams pageParams) {
        Page<Article> page = new Page<>(pageParams.getPage(), pageParams.getPageSize());
        IPage<Article> articleIPage = articleMapper.listHotArticlePage(page);
        List<Article> records = articleIPage.getRecords();
        List<ArticleVo> articleVoList = copyList(records);
        return Result.success(articleVoList);
    }

    /**
     * 查询某个分类下的所有分章
     * @param categoryId
     * @return
     */
    @Override
    public Result getArticleListByCategory(int categoryId) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getCategoryId,categoryId);
        List<Article> articleList = articleMapper.selectList(queryWrapper);
        List<ArticleVo> articleVoList = copyList(articleList);
        return Result.success(articleVoList);
    }

    /**
     * 归档查询
     */
    @Override
    public Result listArchive() {
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.orderByDesc(Article::getCreateTime);
        List<Article> articleList = articleMapper.selectList(lambdaQueryWrapper);
        List<ArchiveVo> archiveVoList = new ArrayList<>();

        for (Article article : articleList) {
            ArchiveVo archiveVo = new ArchiveVo();
            BeanUtils.copyProperties(article,archiveVo);
            archiveVo.setCreateTime(new DateTime(article.getCreateTime()).toString("yyyy-MM-dd HH:mm"));
            archiveVo.setAuthor(sysUserService.findAuthorNameById(article.getAuthorId()));
            archiveVoList.add(archiveVo);
        }

        return Result.success(archiveVoList);
    }

    /**
     * 发布文章
     * @param articleParam
     * @return
     */
    @Override
    public Result publish(ArticleParam articleParam) {
        /**
         * 1. 得到当前的登陆用户
         * 2. 将标签加入到关联映射表
         * 3. 内容存储
         */
        // 获得当前登陆用户
        SysUser sysUser = UserThreadLocal.get();
        Article article = new Article();
        article.setAuthorId(sysUser.getId());

        article.setTitle(articleParam.getTitle());
        article.setSummary(articleParam.getSummary());
        article.setLikeCounts(0);
        article.setViewCounts(0);
        article.setCreateTime(System.currentTimeMillis());
        article.setLastEditTime(System.currentTimeMillis());
        article.setCategoryId(articleParam.getCategory());

        // 生成一个文章并获得相应的id
        this.articleMapper.insert(article);

        //tag
        List<Tag> tags = articleParam.getTag();
        if (tags != null){
            for (Tag tag : tags) {
                ArticleTag articleTag = new ArticleTag();
                articleTag.setTagId(tag.getId());
                articleTag.setArticleId(article.getId());
                articleTagMapper.insert(articleTag);
            }
        }

        // content
        ArticleContent articleContent = new ArticleContent();
        articleContent.setArticleId(article.getId());
        articleContent.setContent(articleParam.getContentParam().getContent());
        articleContent.setContentHtml(articleParam.getContentParam().getContentHtml());
        articleContentMapper.insert(articleContent);

        article.setContentId(articleContent.getId());
        articleMapper.updateById(article);
        Map<String, String> map = new HashMap<>();
        map.put("id",article.getId()+"");
        return Result.success(map);
    }

    /**
     * 查看文章详情
     * @param articleId
     * @return
     */
    @Override
    public Result getArticle(int articleId) {
        /**
         * 1. 根据id查询 文章信息
         * 2. 根据content_id categort_id author_id 做关联查询
         */
        Article article = articleMapper.selectById(articleId);
        ArticleVo articleVo = copy(article,true,true,true,true);
        // 查看文章之后更新浏览量
        // 查看完文章应该返回文章数据，但是增加了更新浏览量的操作，数据库在更新时会加写锁，阻塞其他读操作，性能因此降低，无法避免
        // 这个接口业务耗时也会增加，而且一旦更新浏览量这个操作出错，也会影响返回文章数据的操作，因此可以使用线程池来解决
        // 把更新浏览量这个操作放到线程池中执行，因此和主线程就不相关了
        threadService.updateArticleViewCount(articleMapper,article);
        return Result.success(articleVo);
    }

    private List<ArticleVo> copyList(List<Article> records) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article record : records) {
            articleVoList.add(copy(record,true,true,true,false));
        }
        return articleVoList;
    }

    private ArticleVo copy(Article article, boolean isCategory, boolean isAuthor, boolean isTag,boolean isContent){
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(article,articleVo);
        articleVo.setCreateTime(new DateTime(article.getCreateTime()).toString("yyyy-MM-dd"));
        articleVo.setLastEditTime(new DateTime(article.getLastEditTime()).toString("yyyy-MM-dd"));

        if (isCategory){
            int categoryId = article.getCategoryId();
            Category category= categoryService.findCategoryNameById(categoryId);
            articleVo.setCategory(category.getCategory());
        }
        if (isAuthor){
            int authorId = article.getAuthorId();
            articleVo.setAuthor(sysUserService.findAuthorNameById(authorId));
        }
        if (isTag){
            int articleId = article.getId();
            articleVo.setTagList(tagService.findTagByArticleId(articleId));
        }
        if (isContent){
            int contentId = article.getContentId();
            articleVo.setContentHtml(findArticleContentById(contentId));
        }
        return articleVo;
    }

    private ArticleContentVo findArticleContentById(int contentId) {
        ArticleContent articleContent = articleContentMapper.selectById(contentId);
        ArticleContentVo articleContentVo = new ArticleContentVo();
        articleContentVo.setContentHtml(articleContent.getContentHtml());
        return articleContentVo;
    }

    /**
     * 搜索文章
     */
    @Override
    public Result searchArticle(String keyword) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(Article::getTitle,keyword);
        List<Article> articleList = articleMapper.selectList(queryWrapper);
        List<ArticleVo> articleVoList = copyList(articleList);
        return Result.success(articleVoList);
    }

    /**
     * 点赞
     * @param articleId
     * @return
     */
    @Override
    public Result clickHit(int articleId) {
        Article article = articleMapper.selectById(articleId);
        Integer likeCounts = article.getLikeCounts();
        Article articleUpdate = new Article();
        articleUpdate.setLikeCounts(likeCounts + 1);
        LambdaUpdateWrapper<Article> updateWrapper = new LambdaUpdateWrapper<Article>();
        updateWrapper.eq(Article::getId,article.getId());
        updateWrapper.eq(Article::getLikeCounts,likeCounts);
        int i = articleMapper.update(articleUpdate, updateWrapper);
        if (i > 0){
            return Result.success("点赞成功");
        }
        return null;
    }
}
