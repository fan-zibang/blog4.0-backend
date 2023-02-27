package com.BrantleyFan.blog.vo;

import lombok.Data;

import java.util.List;

@Data
public class CommentVo {

    private Integer id;

    private String message;

    private String username;

    private String createTime;

    private List<ReplyCommentVo> commentVoList;

    private Byte author;

}
