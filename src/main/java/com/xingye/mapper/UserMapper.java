package com.xingye.mapper;

import com.xingye.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {

    // 根据用户名和密码查询用户（登录用）
    User findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    // 新增用户
    int insertUser(User user);

    // 根据 id 删除用户
    int deleteUserById(Integer id);

    // 修改用户信息
    int updateUser(User user);

    // 根据 id 查询用户
    User selectUserById(Integer id);

    // 查询所有用户
    List<User> selectAllUsers();

    // 分页查询用户
    List<User> selectUsersByPage(@Param("start") int start, @Param("pageSize") int pageSize);

    // 查询用户总数
    long selectTotalUsers();
}
