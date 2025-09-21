package com.xingye;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.xingye.mapper")
public class LoginCodeApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoginCodeApplication.class, args);
        System.out.println("------------=========hello");
    }

}
