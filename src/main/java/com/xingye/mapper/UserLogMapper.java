package com.xingye.mapper;

import com.xingye.pojo.UserLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserLogMapper {

    int insert(UserLog userLog);

    int deleteById(Integer id);

    int update(UserLog userLog);

    UserLog selectById(Integer id);

    List<UserLog> selectAll();

    List<UserLog> selectByUserId(Integer userId);

    // ---------------------- 新增分页相关方法 ----------------------
    /**
     * 全量日志分页查询
     * @param start 起始索引（从0开始）
     * @param pageSize 每页条数
     * @return 当前页日志列表
     */
    List<UserLog> selectByPage(@Param("start") int start, @Param("pageSize") int pageSize);

    /**
     * 指定用户的日志分页查询
     * @param userId 用户ID
     * @param start 起始索引（从0开始）
     * @param pageSize 每页条数
     * @return 当前页日志列表
     */
    List<UserLog> selectByUserIdAndPage(
            @Param("userId") Integer userId,
            @Param("start") int start,
            @Param("pageSize") int pageSize
    );

    List<UserLog> selectByFiltersAndPage(@Param("start") int start, @Param("pageSize") int pageSize,
                                         @Param("username") String username, @Param("city") String city, @Param("ip") String ip);

    long selectTotalCountByFilters(@Param("username") String username, @Param("city") String city, @Param("ip") String ip);


    /**
     * 查询日志总记录数（全量）
     * @return 总记录数
     */
    long selectTotalCount();

    /**
     * 查询指定用户的日志总记录数
     * @param userId 用户ID
     * @return 该用户的日志总记录数
     */
    long selectTotalCountByUserId(Integer userId);
}