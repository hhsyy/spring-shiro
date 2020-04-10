package com.yiyuclub.springshiro;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan(basePackages = {"com.yiyuclub.springshiro.mapper"})
public class SpringShiroApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringShiroApplication.class, args);
    }

}
