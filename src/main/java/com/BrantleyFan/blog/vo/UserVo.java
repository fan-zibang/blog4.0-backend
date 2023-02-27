package com.BrantleyFan.blog.vo;

import lombok.Data;

@Data
public class UserVo {
    private Integer id;

    private String account;

    private String password;

    private byte admin;

    private String email;

    private String mobilePhone;

    private String nickname;

}
