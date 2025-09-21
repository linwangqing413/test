package com.xingye.util;

import com.macasaet.fernet.Key;
import com.macasaet.fernet.Token;
import com.macasaet.fernet.Validator;

import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;
import java.util.function.Function;

public class FernetUtils {

    // ⚠️ 必须和前端/加密端保持一致
    private static final String FERNET_BASE64_KEY = "uRRcJrGsxQhIGLUcivRxq-NJ_5GmZftGK0nv2_yetXc=";
//    private static final String FERNET_BASE64_KEY = "LA7ieEMX4V2tyARRsmuRVquL7TWcaqTmv6zRbDT83fU=";
    public static final Key FERNET_KEY = new Key(FERNET_BASE64_KEY);

    /**
     * 用于解密的 Validator
     */
    public static class StringValidator implements Validator<String> {
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
            return this::convert;
        }
    }

    /**
     * Fernet 解密
     */
    public static String decrypt(String encryptedData) {
        Token token = Token.fromString(encryptedData);
        return token.validateAndDecrypt(FERNET_KEY, new StringValidator());
    }

    /**
     * Fernet 加密
     */
    public static String encrypt(String plaintext) {
        SecureRandom secureRandom = new SecureRandom();
        Token token = Token.generate(secureRandom, FERNET_KEY, plaintext.getBytes());
        return token.serialise();
    }
}
