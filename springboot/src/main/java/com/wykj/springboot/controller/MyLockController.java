package com.wykj.springboot.controller;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonView;
import com.wykj.springboot.cfg.annotation.MyLockAnnotation;
import com.wykj.springboot.cfg.annotation.MyMethodAnnotation;
import com.wykj.springboot.dto.ApiResponse;
import com.wykj.springboot.entity.Student;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/ctrl")
public class MyLockController {
    @Autowired
    Student studentEntity;

    @Value("${student.id:'id0001'}")
    String student;

    @PostMapping(path = "/lock")
    @ResponseBody
    @MyLockAnnotation(prefix="")
    public ApiResponse<Student> addStudent(@RequestBody @Validated  Student student, BindingResult result) throws Exception {
        if (result != null) {
            String reMsg = JSON.toJSONString(
                    result.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage())
                            .collect(Collectors.toList()));
            // return ApiResponse.error(reMsg);
            throw new Exception(reMsg);
        }
        throw new IllegalArgumentException("参数错误");
//        System.out.println(student.toString());
//        return student;
    }

}