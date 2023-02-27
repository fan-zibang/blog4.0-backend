package com.BrantleyFan.blog.vo.params;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class SysUserParam {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String account;

    private String password;

    private String email;

    private String mobilePhone;

    private String nickname;

}
