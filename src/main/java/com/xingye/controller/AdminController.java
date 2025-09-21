package com.xingye.controller;

import com.xingye.pojo.Admin;
import com.xingye.pojo.Result;
import com.xingye.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController


public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/admin/login")
    public Result login(@RequestBody Admin admin) {

        String username = admin.getUsername();
        String password = admin.getPassword();
        Admin admin1 = adminService.login(username, password);

        if (admin1 != null) {
            // 登录成功，返回 admin 数据
            return Result.success(admin1);
        } else {
            // 登录失败，返回错误信息
            return Result.error("Username or password incorrect");
        }
    }
}
