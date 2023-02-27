package com.BrantleyFan.blog.controller;

import com.BrantleyFan.blog.service.SysUserService;
import com.BrantleyFan.blog.vo.Result;
import com.BrantleyFan.blog.vo.params.PasswordParams;
import com.BrantleyFan.blog.vo.params.SysUserParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @GetMapping("/listUser")
    public Result listUser(){
        return sysUserService.listUser();
    }

    @PostMapping("/currentUser")
    public Result currentUser(@RequestHeader("Authorization") String token){
        return sysUserService.findSysUserByToken(token);
    }

    @PostMapping("/delete/{id}")
    public Result deleteUser(@PathVariable("id") int id){
        return sysUserService.deleteUser(id);
    }

    @PostMapping("/edit")
    public Result editUser(@RequestBody SysUserParam sysUserParam){
        return sysUserService.editUser(sysUserParam);
    }

    @PostMapping("/editPwd")
    public Result editUserPwd(@RequestBody PasswordParams passwordParams){
        return sysUserService.editUserPwd(passwordParams);
    }
}
