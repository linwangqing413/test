package com.xingye.service.impl;

import com.xingye.mapper.UserMapper;
import com.xingye.pojo.PageResult;
import com.xingye.pojo.User;
import com.xingye.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User login(String username, String password) {
        // 调用mapper查询用户
        return userMapper.findByUsernameAndPassword(username, password);
    }

    @Override
    public int addUser(User user) {
        return userMapper.insertUser(user);
    }

    @Override
    public int removeUserById(Integer id) {
        return userMapper.deleteUserById(id);
    }

    @Override
    public int modifyUser(User user) {
        return userMapper.updateUser(user);
    }

    @Override
    public User queryUserById(Integer id) {
        return userMapper.selectUserById(id);
    }

    @Override
    public List<User> queryAllUsers() {
        return userMapper.selectAllUsers();
    }

    @Override
    public PageResult<User> queryUsersByPage(int pageNum, int pageSize) {
        // 计算起始位置
        int start = (pageNum - 1) * pageSize;
        // 查询当前页数据
        List<User> users = userMapper.selectUsersByPage(start, pageSize);
        // 查询总记录数
        long total = userMapper.selectTotalUsers();
        // 封装分页结果
        return new PageResult<>(total, pageNum, pageSize, users);
    }

    @Override
    public long queryTotalUsers() {
        return userMapper.selectTotalUsers();
    }
}
