package com.xingye.controller;

import com.xingye.util.FernetUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class FernetController {

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 解析加密数据接口
     */
    @PostMapping("/decrypt")
    public Map<String, Object> decryptData(@RequestBody Map<String, String> request) {
        try {
            String encryptedData = request.get("data");
            if (encryptedData == null || encryptedData.trim().isEmpty()) {
                return Map.of("code", 0, "msg", "缺少加密数据");
            }

            String decrypted = FernetUtils.decrypt(encryptedData);
            return objectMapper.readValue(decrypted, Map.class);

        } catch (Exception e) {
            e.printStackTrace();
            return Map.of("code", 0, "msg", "解密失败: " + e.getMessage());
        }
    }

    /**
     * Fernet 加密接口
     * 前端传：
     * {
     *    "data": {...}   // 任意 JSON
     * }
     * 返回：
     * {
     *    "code": 1,
     *    "msg": "Success",
     *    "encrypted": "<Fernet加密字符串>"
     * }
     */
    @PostMapping("/encrypt")
    public Map<String, Object> encryptData(@RequestBody Map<String, Object> request) {
        try {
            if (request == null || request.isEmpty()) {
                return Map.of("code", 0, "msg", "缺少要加密的数据");
            }

            // 将 Map 转成 JSON 字符串
            String json = objectMapper.writeValueAsString(request);

            // 调用 FernetUtils 加密
            String encrypted = FernetUtils.encrypt(json);

            return Map.of(
                    "code", 1,
                    "msg", "Success",
                    "encrypted", encrypted
            );

        } catch (Exception e) {
            e.printStackTrace();
            return Map.of("code", 0, "msg", "加密失败: " + e.getMessage());
        }
    }
}
