package com.wykj.springboot.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.wykj.springboot.annotation.MyMethodAnnotation;
import com.wykj.springboot.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/ctrl")
public class MyController {
    @Autowired
    Student studentEntity;

    @Value("${student.id:'id0001'}")
    String student;

    /**
     * 获取应用的参数
     */
    @Autowired
    ApplicationArguments applicationArguments;

    @GetMapping(path = "/getStudent")
    @MyMethodAnnotation(value = "isMe")
    public Student getStudent(String str) {
        System.out.println("str " + str);
        return studentEntity;
    }

    @GetMapping("/getVal")
    public String getValue() {
        applicationArguments.getNonOptionArgs();
        return student;
    }


    @GetMapping(path = "/getStudentBase")
    @JsonView(Student.StudentBase.class)
    public Student getStudentBase(String str) {
        System.out.println("str " + str);
        return studentEntity;
    }

    @GetMapping(path = "/getStudentAll")
    @JsonView(Student.StudentAll.class)
    public Student getStudentAll(String str) {
        System.out.println("str " + str);
        return studentEntity;
    }

    @PostMapping(path = "/addStudent")
    @ResponseBody
    public Student addStudent(@Valid @RequestBody Student student, BindingResult result) {
        if (result != null) {
            result.getAllErrors().forEach(error-> System.out.println(error.getDefaultMessage()));
        }
        throw new IllegalArgumentException("参数错误");
//        System.out.println(student.toString());
//        return student;
    }

    @PostMapping(path = "/addStudentWithoutBindingResult")
    @ResponseBody
    public Student addStudentWithoutBindingResult(@Valid @RequestBody Student student) {
        System.out.println(student.toString());
        return student;
    }
}
