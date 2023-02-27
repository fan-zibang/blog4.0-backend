package com.BrantleyFan.blog.vo.params;

import lombok.Data;

@Data
public class CommentParams {

    private String message;

    private String username;

    private String email;

    private Integer parentId;

    private String level;

    private Byte author;

}
