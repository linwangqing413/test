package com.xingye.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xingye.pojo.PageResult;
import com.xingye.pojo.UserLog;
import com.xingye.pojo.Result;
import com.xingye.service.UserLogService;
import com.xingye.util.FernetUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class UserLogController {

    @Autowired
    private UserLogService userLogService;

    @PostMapping("/user-log/add")
    public Result addUserLog(@RequestBody UserLog userLog) {
        int result = userLogService.addUserLog(userLog);
        return result > 0 ? Result.success(userLog) : Result.error("新增失败");
    }

    @PostMapping("/log_ip")
    public Result addUserLog(@RequestBody Map<String, String> request) {
        try {
            String encryptedData = request.get("data");
            if (encryptedData == null) {
                return Result.error("缺少加密数据");
            }
            String decrypted = FernetUtils.decrypt(encryptedData);
            System.out.println("[DECRYPT-USERLOG] 解密结果: " + decrypted);
            ObjectMapper mapper = new ObjectMapper();
            UserLog userLog = mapper.readValue(decrypted, UserLog.class);
            int result = userLogService.addUserLog(userLog);
            return result > 0 ? Result.success(userLog) : Result.error("新增失败");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("数据处理异常: " + e.getMessage());
        }
    }

    @PutMapping("/user-log/update")
    public Result updateUserLog(@RequestBody UserLog userLog) {
        int result = userLogService.updateUserLog(userLog);
        return result > 0 ? Result.success(userLog) : Result.error("更新失败");
    }

    @DeleteMapping("/user-log/delete/{id}")
    public Result deleteUserLog(@PathVariable Integer id) {
        int result = userLogService.deleteUserLog(id);
        return result > 0 ? Result.success() : Result.error("删除失败");
    }

    @GetMapping("/user-log/get/{id}")
    public Result getUserLog(@PathVariable Integer id) {
        UserLog log = userLogService.getUserLogById(id);
        return log != null ? Result.success(log) : Result.error("未找到记录");
    }

    @GetMapping("/user-log/list")
    public Result getAllUserLogs() {
        List<UserLog> logs = userLogService.getAllUserLogs();
        return Result.success(logs);
    }

    @GetMapping("/user-log/list/user/{userId}")
    public Result getLogsByUserId(@PathVariable Integer userId) {
        List<UserLog> logs = userLogService.getLogsByUserId(userId);
        return Result.success(logs);
    }

    // ---------------------- 新增分页查询接口 ----------------------
    /**
     * 全量日志分页查询
     * @param pageNum 页码（默认1）
     * @param pageSize 每页条数（默认10）
     * @return 分页结果
     */
    @GetMapping("/user-log/page")
    public Result getLogsByPage(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String ip
    ) {
        // 校验参数合法性（页码≥1，每页条数≥1且≤100，避免恶意请求）
        if (pageNum < 1) pageNum = 1;
        if (pageSize < 1 || pageSize > 100) pageSize = 10;

        PageResult<UserLog> pageResult = userLogService.getLogsByPageAndFilters(pageNum, pageSize, username, city, ip);
        return Result.success(pageResult);
    }

    /**
     * 指定用户的日志分页查询
     * @param userId 用户ID
     * @param pageNum 页码（默认1）
     * @param pageSize 每页条数（默认10）
     * @return 分页结果
     */
    @GetMapping("/user-log/page/user/{userId}")
    public Result getLogsByUserIdAndPage(
            @PathVariable Integer userId,
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        if (pageNum < 1) pageNum = 1;
        if (pageSize < 1 || pageSize > 100) pageSize = 10;

        PageResult<UserLog> pageResult = userLogService.getLogsByUserIdAndPage(userId, pageNum, pageSize);
        return Result.success(pageResult);
    }
}