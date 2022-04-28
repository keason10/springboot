package com.wykj.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

// @SpringBootApplication(scanBasePackageClasses={WebConfig.class})
@SpringBootApplication
@EnableScheduling
/*
1. maven添加jar
2. @EnableSwagger2
3  访问 http://localhost:port/swagger-ui.html
4. 文档生成用swagger注解
 * */
@EnableSwagger2
public class SpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootApplication.class, args);
    }
}
