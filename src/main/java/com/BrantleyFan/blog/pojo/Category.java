package com.BrantleyFan.blog.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Category {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String category;

}
