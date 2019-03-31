package com.wykj.springboot.callback;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 注入参数，可以做spring boot 启动之前最后一次参上校验
 */
@Order(value = 2)
@Component
public class MyCommandLineRunner implements CommandLineRunner {

    @Value("${student.id}")
    String id;

    @Override
    public void run(String... strings) throws Exception {
        System.out.println(strings);
        if ("keason".equals(id)) {
            throw new IllegalArgumentException("参数错误");
        }

    }
}
