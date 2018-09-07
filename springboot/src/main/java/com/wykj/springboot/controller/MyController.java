package com.wykj.springboot.controller;

import com.wykj.springboot.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/ctrl")
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

    @RequestMapping(path = "/getVal",method = RequestMethod.GET)
    public String getValue() {
        applicationArguments.getNonOptionArgs();
        return student;
    }

    @RequestMapping(path = "/getStudent",method = RequestMethod.GET)
    public Student getStudent() {
        return studentEntity;
    }
}
