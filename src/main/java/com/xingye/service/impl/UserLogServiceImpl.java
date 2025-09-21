package com.xingye.service.impl;

import com.xingye.mapper.UserLogMapper;
import com.xingye.pojo.PageResult;
import com.xingye.pojo.UserLog;
import com.xingye.service.UserLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserLogServiceImpl implements UserLogService {

    @Autowired
    private UserLogMapper userLogMapper;

    @Override
    public int addUserLog(UserLog userLog) {
        return userLogMapper.insert(userLog);
    }

    @Override
    public int updateUserLog(UserLog userLog) {
        return userLogMapper.update(userLog);
    }

    @Override
    public int deleteUserLog(Integer id) {
        return userLogMapper.deleteById(id);
    }

    @Override
    public UserLog getUserLogById(Integer id) {
        return userLogMapper.selectById(id);
    }

    @Override
    public List<UserLog> getAllUserLogs() {
        return userLogMapper.selectAll();
    }

    @Override
    public List<UserLog> getLogsByUserId(Integer userId) {
        return userLogMapper.selectByUserId(userId);
    }
    @Override
    public PageResult<UserLog> getLogsByPageAndFilters(int pageNum, int pageSize, String username, String city, String ip) {
        int start = (pageNum - 1) * pageSize;
        List<UserLog> logList = userLogMapper.selectByFiltersAndPage(start, pageSize, username, city, ip);
        long total = userLogMapper.selectTotalCountByFilters(username, city, ip);
        return new PageResult<>(total, pageNum, pageSize, logList);
    }

    // ---------------------- 实现分页相关方法 ----------------------
    @Override
    public PageResult<UserLog> getLogsByPage(int pageNum, int pageSize) {
        // 计算分页起始位置（MySQL LIMIT 起始索引从0开始）
        int start = (pageNum - 1) * pageSize;
        // 1. 查询当前页数据（按时间倒序，最新日志在前）
        List<UserLog> logList = userLogMapper.selectByPage(start, pageSize);
        // 2. 查询总记录数
        long total = userLogMapper.selectTotalCount();
        // 3. 封装分页结果
        return new PageResult<>(total, pageNum, pageSize, logList);
    }

    @Override
    public PageResult<UserLog> getLogsByUserIdAndPage(Integer userId, int pageNum, int pageSize) {
        int start = (pageNum - 1) * pageSize;
        // 1. 查询指定用户的当前页数据
        List<UserLog> logList = userLogMapper.selectByUserIdAndPage(userId, start, pageSize);
        // 2. 查询指定用户的日志总记录数
        long total = userLogMapper.selectTotalCountByUserId(userId);
        // 3. 封装分页结果
        return new PageResult<>(total, pageNum, pageSize, logList);
    }

    @Override
    public long getTotalLogCount() {
        return userLogMapper.selectTotalCount();
    }

    @Override
    public long getTotalLogCountByUserId(Integer userId) {
        return userLogMapper.selectTotalCountByUserId(userId);
    }
}