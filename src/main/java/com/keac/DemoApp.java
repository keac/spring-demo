package com.keac;

import tk.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
//@MapperScan(basePackages = "com.keac.mapper")
@MapperScan(basePackages = "com.keac.mapper")
@SpringBootApplication
@EnableSwagger2
public class DemoApp {

    public static void main(String[] args) {
        SpringApplication.run(DemoApp.class, args);
    }

}