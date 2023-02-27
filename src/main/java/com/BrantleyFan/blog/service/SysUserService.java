package com.BrantleyFan.blog.service;

import com.BrantleyFan.blog.pojo.SysUser;
import com.BrantleyFan.blog.vo.Result;
import com.BrantleyFan.blog.vo.params.PasswordParams;
import com.BrantleyFan.blog.vo.params.SysUserParam;

public interface SysUserService {

    String findAuthorNameById(int id);

    SysUser findSysUser(String account, String password);

    Result findSysUserByToken(String token);

    Result listUser();

    Result deleteUser(int id);

    Result editUser(SysUserParam sysUserParam);

    Result editUserPwd(PasswordParams passwordParams);
}
