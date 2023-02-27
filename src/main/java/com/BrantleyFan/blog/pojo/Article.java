package com.BrantleyFan.blog.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Article {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String title;

    private String summary;

    private Integer likeCounts;

    private Integer viewCounts;

    private Long createTime;

    private Long lastEditTime;

    private Integer authorId;

    private Integer contentId;

    private Integer categoryId;

}
