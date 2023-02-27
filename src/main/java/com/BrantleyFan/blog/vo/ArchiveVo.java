package com.BrantleyFan.blog.vo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ArchiveVo {

    private Integer id;

    private String title;

    private String createTime;

    private String author;
}
