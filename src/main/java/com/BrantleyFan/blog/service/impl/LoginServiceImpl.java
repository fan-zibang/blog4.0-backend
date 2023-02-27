package com.BrantleyFan.blog.service.impl;

import com.BrantleyFan.blog.mapper.SysUserMapper;
import com.BrantleyFan.blog.pojo.SysUser;
import com.BrantleyFan.blog.service.LoginService;
import com.BrantleyFan.blog.service.SysUserService;
import com.BrantleyFan.blog.utils.JWTUtils;
import com.BrantleyFan.blog.vo.ErrorCode;
import com.BrantleyFan.blog.vo.Result;
import com.BrantleyFan.blog.vo.params.LoginParam;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Map;

@Service
public class LoginServiceImpl implements LoginService {

    private static final String slat = "#!brantleyfan!#";

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public Result login(LoginParam loginParam) {
        /**
         * 1.检查参数是否合法
         * 2.根据用户名和密码查找用户表
         * 3.存在：登陆成功 不存在：登陆失败
         * 4.登陆成功使用jwt生成token返回前端
         * 5.token放入redis token：user信息 设置过期时间（登陆验证的时候，先认证token字符串是否合法，去redis认证是否存在）
         */
        String account = loginParam.getAccount();
        String password = loginParam.getPassword();

        if (StringUtils.isBlank(account) || StringUtils.isBlank(password)){
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
        }
        password = DigestUtils.md5DigestAsHex((password + slat).getBytes());
        SysUser sysUser = sysUserService.findSysUser(account,password);
        if (sysUser == null){
            return Result.fail(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode(), ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
        }

        String token = JWTUtils.createToken(sysUser.getId());

        return Result.success(token);
    }

    @Override
    public SysUser checkToken(String token) {
        if (StringUtils.isBlank(token)){
            return null;
        }
        Map<String, Object> map = JWTUtils.checkToken(token);
        if (map == null){
            return null;
        }
        SysUser sysUser = sysUserMapper.selectOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getId,map.get("userId")));
        return sysUser;
    }
}
