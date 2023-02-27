package com.BrantleyFan.blog.vo.params;

import lombok.Data;

@Data
public class PasswordParams {

    private Integer id;

    private String oldPassword;

    private String newPassword;

}
