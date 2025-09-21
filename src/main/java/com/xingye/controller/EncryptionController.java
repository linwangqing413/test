package com.xingye.controller;

import com.macasaet.fernet.Key;
import com.macasaet.fernet.Token;
import com.macasaet.fernet.Validator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@RestController
public class EncryptionController {

    // 🔐 Fernet 密钥（必须与 Python 端使用的密钥完全一致）
    private static final String FERNET_BASE64_KEY = "uRRcJrGsxQhIGLUcivRxq-NJ_5GmZftGK0nv2_yetXc=";

    // 初始化 Fernet Key
    private static final Key FERNET_KEY = new Key(FERNET_BASE64_KEY);

    /**
     * 接收用户名和密码，拼接后使用 Fernet 加密，返回加密字符串
     */
    @PostMapping("/encrypt-login")
    public ResponseEntity<Map<String, String>> encryptLogin(@RequestBody Map<String, String> credentials) {
        try {
            String username = credentials.get("username");
            String password = credentials.get("password");

            if (username == null || username.trim().isEmpty()) {
                return badRequest("用户名不能为空");
            }
            if (password == null || password.trim().isEmpty()) {
                return badRequest("密码不能为空");
            }

            // 拼接明文: username:password
            String plaintext = username + ":" + password;
            System.out.println("[ENCRYPT] 明文字符串: " + plaintext);

            // 使用 Fernet 加密
            SecureRandom secureRandom = new SecureRandom();
            Token token = Token.generate(secureRandom, FERNET_KEY, plaintext.getBytes());
            String encryptedData = token.serialise();

            System.out.println("[ENCRYPT] 加密结果: " + encryptedData);

            Map<String, String> response = new HashMap<>();
            response.put("data", encryptedData);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            System.err.println("[ENCRYPT] 加密失败: " + e.getMessage());
            return badRequest("加密失败: " + e.getMessage());
        }
    }

    /**
     * 接收加密字符串，解密并返回明文
     */
    /**
     * 接收加密字符串，解密并返回用户名和密码
     */
    @PostMapping("/decrypt-login")
    public ResponseEntity<Map<String, String>> decryptLogin(@RequestBody Map<String, String> request) {
        try {
            String encryptedData = request.get("data");
            if (encryptedData == null || encryptedData.trim().isEmpty()) {
                return badRequest("加密字符串不能为空");
            }

            // 解析 token
            Token token = Token.fromString(encryptedData);

            // 解密
            String decrypted = token.validateAndDecrypt(FERNET_KEY, new StringValidator());

            System.out.println("[DECRYPT] 解密结果: " + decrypted);

            // 拆分 "username:password"
            String[] parts = decrypted.split(":", 2); // 限制只分割一次，避免密码里有冒号的情况
            Map<String, String> response = new HashMap<>();
            response.put("username", parts.length > 0 ? parts[0] : "");
            response.put("password", parts.length > 1 ? parts[1] : "");

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            System.err.println("[DECRYPT] 解密失败: " + e.getMessage());
            return badRequest("解密失败: " + e.getMessage());
        }
    }

    private static class StringValidator implements Validator<String> {

        public String convert(byte[] bytes) {
            return new String(bytes);
        }

        @Override
        public Duration getTimeToLive() {
            return Duration.ofDays(1); // token 有效期 1 天
        }


        public Instant getCurrentTime() {
            return Instant.now();
        }

        @Override
        public Function<byte[], String> getTransformer() {
            // 直接调用 convert 的逻辑
            return this::convert;
        }
    }


    // 辅助方法：返回错误响应
    private ResponseEntity<Map<String, String>> badRequest(String message) {
        Map<String, String> response = new HashMap<>();
        response.put("error", message);
        return ResponseEntity.badRequest().body(response);
    }
}
