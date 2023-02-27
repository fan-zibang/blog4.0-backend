package com.BrantleyFan.blog.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class ArticleContent {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String content;

    private String contentHtml;

    private Integer articleId;

}
