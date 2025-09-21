package com.xingye.mapper;

import com.xingye.pojo.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AdminMapper {
        Admin findByUsernameAndPassword(@Param("username") String username,
                                    @Param("password") String password);
}
