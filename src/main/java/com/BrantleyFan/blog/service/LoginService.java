package com.BrantleyFan.blog.service;

import com.BrantleyFan.blog.pojo.SysUser;
import com.BrantleyFan.blog.vo.Result;
import com.BrantleyFan.blog.vo.params.LoginParam;

public interface LoginService {
    Result login(LoginParam loginParam);

    SysUser checkToken(String token);
}
