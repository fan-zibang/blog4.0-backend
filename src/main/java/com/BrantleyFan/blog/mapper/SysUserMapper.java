package com.BrantleyFan.blog.mapper;

import com.BrantleyFan.blog.pojo.SysUser;
import com.BrantleyFan.blog.vo.params.PasswordParams;
import com.BrantleyFan.blog.vo.params.SysUserParam;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

public interface SysUserMapper extends BaseMapper<SysUser> {
    String findAuthorNameById(int id);

    int updateSysUserById(SysUserParam sysUserParam);

    int updatePasswordById(PasswordParams passwordParams);
}
