package com.wykj.springboot.controller;

import cn.hutool.core.collection.ListUtil;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapType;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.wykj.springboot.cfg.annotation.MyMethodAnnotation;
import com.wykj.springboot.dto.ApiResponse;
import com.wykj.springboot.entity.Student;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
public class MyController {

    @Autowired
    @Qualifier("student")
    Student studentEntity;

    @Autowired
    private ObjectMapper objectMapper;

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


    /**
     * 复杂的TypeReference
     * @link {https://hicode.club/articles/2018/03/18/1550590751627.html}
     * @param str
     * @return
     */
    @GetMapping(path = "/showJackson")
    public Student showJackson(String str) throws JsonProcessingException {
        List<Student> list = Arrays.asList(studentEntity);
        String listStr = objectMapper.writeValueAsString(list);

        CollectionType studentCollType = objectMapper.getTypeFactory()
                .constructCollectionType(List.class, Student.class);
        List<Student> listStudents = objectMapper.readValue(listStr, studentCollType);

        ArrayList<ImmutableMap<String, Student>> studentArrayMap = Lists.newArrayList(ImmutableMap.of("student", this.studentEntity));
        String studentArrayMapStr = objectMapper.writeValueAsString(studentArrayMap);
        JavaType javaType = objectMapper.getTypeFactory()
                .constructParametricType(HashMap.class, String.class, Student.class);
        JavaType javaTypeTwo = objectMapper.getTypeFactory().constructParametricType(List.class, javaType);
        ArrayList<ImmutableMap<String, String>> studentArrayMapTwo = objectMapper.readValue(studentArrayMapStr,
                javaTypeTwo);

        Map<String, List<Student>> stringListMap = ImmutableMap.of("students", Arrays.asList(studentEntity));
        String stringListMapStr = objectMapper.writeValueAsString(stringListMap);
        JavaType javaTypeThree = objectMapper.getTypeFactory()
                .constructParametricType(List.class, Student.class);
        JavaType javaTypeThreeL = objectMapper.getTypeFactory()
                .constructMapLikeType(HashMap.class, objectMapper.getTypeFactory().constructType(String.class),
                        javaTypeThree);
        Map<String, List<Student>>  stringListMapStrObj = objectMapper.readValue(stringListMapStr, javaTypeThreeL);


        return null;
    }

    @PostMapping(path = "/addStudent")
    @ResponseBody
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

    @PostMapping(path = "/addStudentWithoutBindingResult")
    @ResponseBody
    public Student addStudentWithoutBindingResult(@Valid @RequestBody Student student) {
        System.out.println(student.toString());
        return student;
    }



}
