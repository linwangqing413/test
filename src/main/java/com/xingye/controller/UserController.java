package com.xingye.controller;

import com.macasaet.fernet.Token;
import com.xingye.pojo.PageResult;
import com.xingye.pojo.Result;
import com.xingye.pojo.User;
import com.xingye.service.UserService;
import com.xingye.util.FernetUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    // 登录接口
    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> request) {
        try {
            // 1. 获取前端传过来的加密数据
            String encryptedData = request.get("data");
            if (encryptedData == null || encryptedData.trim().isEmpty()) {
                String error = "{\"code\":0,\"msg\":\"缺少登录数据\"}";
                return Map.of("data", FernetUtils.encrypt(error));
            }

            // 2. 解密前端数据
            String decrypted = FernetUtils.decrypt(encryptedData);
            System.out.println("[DECRYPT-LOGIN] 解密结果: " + decrypted);

            // 3. 拆分用户名和密码
            String[] parts = decrypted.split(":", 2);
            String username = parts.length > 0 ? parts[0] : "";
            String password = parts.length > 1 ? parts[1] : "";

            // 4. 数据库验证
            User loginUser = userService.login(username, password);

            // 5. 构造原始 JSON 响应
            String plainResponse;
            if (loginUser != null) {
                plainResponse = "{\"code\":1,\"msg\":\"Success\"}";
            } else {
                plainResponse = "{\"code\":0,\"msg\":\"用户名或密码错误\"}";
            }

            // 6. 加密返回
            String encryptedResponse = FernetUtils.encrypt(plainResponse);
            return Map.of("data", encryptedResponse);

        } catch (Exception e) {
            e.printStackTrace();
            String errorResponse = "{\"code\":0,\"msg\":\"解密或登录失败: " + e.getMessage() + "\"}";
            String encryptedError = FernetUtils.encrypt(errorResponse);
            return Map.of("data", encryptedError);
        }
    }

    @GetMapping("/ping")
    public String ping() {
        return "OK";
    }

    // 新增用户
    @PostMapping("/user/add")
    public Result addUser(@RequestBody User user) {
        int result = userService.addUser(user);
        return result > 0 ? Result.success() : Result.error("新增用户失败");
    }

    // 根据 id 删除用户
    @DeleteMapping("/user/delete/{id}")
    public Result deleteUserById(@PathVariable Integer id) {
        int result = userService.removeUserById(id);
        return result > 0 ? Result.success() : Result.error("删除用户失败");
    }

    // 修改用户信息
    @PutMapping("/user/update")
    public Result updateUser(@RequestBody User user) {
        int result = userService.modifyUser(user);
        return result > 0 ? Result.success() : Result.error("修改用户失败");
    }

    // 根据 id 查询用户
    @GetMapping("/user/query/{id}")
    public Result queryUserById(@PathVariable Integer id) {
        User user = userService.queryUserById(id);
        return user != null ? Result.success(user) : Result.error("未查询到该用户");
    }

    // 查询所有用户
    @GetMapping("/user/query/all")
    public Result queryAllUsers() {
        List<User> users = userService.queryAllUsers();
        return Result.success(users);
    }

    // 分页查询用户
    @GetMapping("/user/query/page")
    public Result queryUsersByPage(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize) {
        PageResult<User> pageResult = userService.queryUsersByPage(pageNum, pageSize);
        return Result.success(pageResult);
    }
}
