package com.BrantleyFan.blog.vo.params;

import com.BrantleyFan.blog.pojo.Category;
import com.BrantleyFan.blog.pojo.Tag;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class ArticleParam {

    private int id;

    private ArticleContentParam contentParam;

    private int category;

    private String summary;

    private List<Tag> tag;

    private String title;
}
