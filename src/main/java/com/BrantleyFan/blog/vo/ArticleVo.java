package com.BrantleyFan.blog.vo;

import lombok.Data;

import java.util.List;

@Data
public class ArticleVo {

    private Integer id;

    private String title;

    private String summary;

    private Integer likeCounts;

    private Integer viewCounts;

    private String createTime;

    private String lastEditTime;

    private String author;

    private String category;

    private List<TagVo> tagList;

    private ArticleContentVo contentHtml;

}
