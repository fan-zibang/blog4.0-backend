package com.BrantleyFan.blog.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Comment {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String message;

    private String username;

    private String email;

    private Long createTime;

    private Integer parentId;

    private String level;

    private Byte author;
}
