package com.BrantleyFan.blog.vo;

import lombok.Data;

@Data
public class ReplyCommentVo {

    private Integer id;

    private String message;

    private String username;

    private String createTime;

    private Byte author;

}
