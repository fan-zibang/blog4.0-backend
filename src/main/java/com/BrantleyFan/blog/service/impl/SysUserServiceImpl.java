package com.BrantleyFan.blog.service.impl;

import com.BrantleyFan.blog.mapper.SysUserMapper;
import com.BrantleyFan.blog.pojo.SysUser;
import com.BrantleyFan.blog.service.LoginService;
import com.BrantleyFan.blog.service.SysUserService;
import com.BrantleyFan.blog.vo.ErrorCode;
import com.BrantleyFan.blog.vo.LoginUserVo;
import com.BrantleyFan.blog.vo.Result;
import com.BrantleyFan.blog.vo.UserVo;
import com.BrantleyFan.blog.vo.params.PasswordParams;
import com.BrantleyFan.blog.vo.params.SysUserParam;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class SysUserServiceImpl implements SysUserService {

    private static final String slat = "#!brantleyfan!#";

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private LoginService loginService;

    @Override
    public String findAuthorNameById(int id) {
        return sysUserMapper.findAuthorNameById(id);
    }

    @Override
    public SysUser findSysUser(String account, String password) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount,account);
        queryWrapper.eq(SysUser::getPassword,password);
        queryWrapper.select(SysUser::getAccount,SysUser::getId,SysUser::getNickname,SysUser::getAvatar);
        queryWrapper.last("limit 1");
        return sysUserMapper.selectOne(queryWrapper);
    }

    @Override
    public Result findSysUserByToken(String token) {
        /**
         * 1.token合法性校验
         *      是否为空，解析是否成功
         * 2.如果校验失败，返回错误
         * 3.如果成功，返回对应的结果 LoginUserVo
         *
         */

        SysUser sysUser = loginService.checkToken(token);
        if (sysUser == null){
            Result.fail(ErrorCode.TOKEN_ERROR.getCode(), ErrorCode.TOKEN_ERROR.getMsg());
        }
        LoginUserVo loginUserVo = new LoginUserVo();
        loginUserVo.setId(sysUser.getId());
        loginUserVo.setAccount(sysUser.getAccount());
        loginUserVo.setNickname(sysUser.getNickname());
        loginUserVo.setAvatar(sysUser.getAvatar());
        loginUserVo.setAdmin(sysUser.getAdmin());
        loginUserVo.setLastLogin(new DateTime(sysUser.getLastLogin()).toString("yyyy-MM-dd HH:mm"));
        return Result.success(loginUserVo);
    }

    @Override
    public Result listUser() {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(SysUser::getId,SysUser::getAccount,SysUser::getPassword,SysUser::getNickname,
                SysUser::getMobilePhone,SysUser::getEmail,SysUser::getAdmin);
        List<SysUser> sysUserList = sysUserMapper.selectList(queryWrapper);
        List<UserVo> userVoList = new ArrayList<>();
        for (SysUser sysUser : sysUserList) {
            UserVo userVo = new UserVo();
            BeanUtils.copyProperties(sysUser,userVo);
            userVoList.add(userVo);
        }
        return Result.success(userVoList);
    }

    /**
     * 修改用户信息
     * @param sysUserParam
     * @return
     */
    @Override
    public Result editUser(SysUserParam sysUserParam) {
        int i = sysUserMapper.updateSysUserById(sysUserParam);
        if (i > 0){
            Result.success("用户信息更新成功");
        }
        return null;
    }

    /**
     * 删除用户
     * @param id
     * @return
     */
    @Override
    public Result deleteUser(int id) {
        int i = sysUserMapper.deleteById(id);
        if (i > 0){
            return Result.success("删除用户成功");
        }
        return null;
    }

    /**
     * 修改密码
     */
    @Override
    public Result editUserPwd(PasswordParams passwordParams) {
        /**
         * 1.验证旧密码是否正确
         * 2.旧密码错误，返回错误
         * 3.旧密码正确，更新旧密码
         */
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(SysUser::getPassword);
        queryWrapper.eq(SysUser::getId,passwordParams.getId());
        SysUser sysUser = sysUserMapper.selectOne(queryWrapper);
        String password = sysUser.getPassword();
        String oldPassword = DigestUtils.md5DigestAsHex((passwordParams.getOldPassword() + slat).getBytes());
        if (password.equals(oldPassword)){
            String newPassword = DigestUtils.md5DigestAsHex((passwordParams.getNewPassword() + slat).getBytes());
            passwordParams.setNewPassword(newPassword);
            int i = sysUserMapper.updatePasswordById(passwordParams);
            if (i > 0){
                return Result.success("密码修改成功");
            }
        }else {
            return Result.fail(70001,"旧密码错误");
        }
        return null;
    }
}
