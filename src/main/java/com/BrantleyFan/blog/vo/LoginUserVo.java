package com.BrantleyFan.blog.vo;

import lombok.Data;

@Data
public class LoginUserVo {

    private Integer id;

    private String account;

    private String nickname;

    private String avatar;

    private byte admin;

    private String lastLogin;

}
