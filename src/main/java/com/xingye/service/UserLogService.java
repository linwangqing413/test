package com.xingye.service;

import com.xingye.pojo.PageResult;
import com.xingye.pojo.UserLog;

import java.util.List;

public interface UserLogService {

    int addUserLog(UserLog userLog);

    int updateUserLog(UserLog userLog);

    int deleteUserLog(Integer id);

    UserLog getUserLogById(Integer id);

    List<UserLog> getAllUserLogs();

    List<UserLog> getLogsByUserId(Integer userId);

    // ---------------------- 新增分页相关方法 ----------------------
    /**
     * 全量日志分页查询
     * @param pageNum 页码（从1开始）
     * @param pageSize 每页条数
     * @return 分页结果（含总记录数、当前页数据等）
     */
    PageResult<UserLog> getLogsByPage(int pageNum, int pageSize);

    /**
     * 指定用户的日志分页查询
     * @param userId 用户ID
     * @param pageNum 页码（从1开始）
     * @param pageSize 每页条数
     * @return 分页结果（含总记录数、当前页数据等）
     */
    PageResult<UserLog> getLogsByUserIdAndPage(Integer userId, int pageNum, int pageSize);

    /**
     * 查询日志总记录数（全量）
     * @return 总记录数
     */

    PageResult<UserLog> getLogsByPageAndFilters(int pageNum, int pageSize, String username, String city, String ip);



    long getTotalLogCount();

    /**
     * 查询指定用户的日志总记录数
     * @param userId 用户ID
     * @return 该用户的日志总记录数
     */
    long getTotalLogCountByUserId(Integer userId);
}