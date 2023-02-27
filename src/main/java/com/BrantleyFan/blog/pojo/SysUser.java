package com.BrantleyFan.blog.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class SysUser {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String account;

    private String password;

    private byte admin;

    private String avatar;

    private String email;

    private String mobilePhone;

    private String nickname;

    private Long lastLogin;
}
