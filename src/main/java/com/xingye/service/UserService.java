package com.xingye.service;

import com.xingye.pojo.PageResult;
import com.xingye.pojo.User;

import java.util.List;

public interface UserService {

    // 登录方法
    User login(String username, String password);

    // 新增用户
    int addUser(User user);

    // 根据 id 删除用户
    int removeUserById(Integer id);

    // 修改用户信息
    int modifyUser(User user);

    // 根据 id 查询用户
    User queryUserById(Integer id);

    // 查询所有用户
    List<User> queryAllUsers();

    // 分页查询用户
    PageResult<User> queryUsersByPage(int pageNum, int pageSize);

    // 查询用户总数
    long queryTotalUsers();
}
