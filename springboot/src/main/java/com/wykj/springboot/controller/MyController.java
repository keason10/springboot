package com.wykj.springboot.controller;

import com.wykj.springboot.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {
    @Autowired
    Student studentEntity;

    @Value("${student.id}")
    String student;

    /**
     * 获取应用的参数
     */
    @Autowired
    ApplicationArguments applicationArguments;

    @GetMapping(path = "/getVal")
    public String getValue() {
        applicationArguments.getNonOptionArgs();
        return student;
    }

    @GetMapping(path = "/getStudent")
    public Student getStudent() {
        return studentEntity;
    }
}
