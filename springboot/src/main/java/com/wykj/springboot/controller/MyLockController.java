package com.wykj.springboot.controller;

import cn.hutool.core.thread.ThreadUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.google.common.collect.ImmutableMap;
import com.wykj.springboot.dao.UserDao;
import com.wykj.springboot.dto.ApiResponse;
import com.wykj.springboot.entity.Student;
import com.wykj.springboot.entity.UserEntity;
import com.wykj.springboot.utils.lock.annotation.MyLockAnnotation;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping(path = "/lock")
public class MyLockController {
    @Autowired
    @Qualifier("student")
    Student studentEntity;

    @Value("${student.id:'id0001'}")
    String student;

    @Autowired
    private UserDao userDao;

    @PostMapping(path = "/user")
    @ResponseBody
    @MyLockAnnotation(prefix = "lock:redis:spring_boot_student_", keyJSONPath = "$.id", lockSeconds = 5)
    @Transactional
    public ApiResponse<ImmutableMap> tryLockUser(@RequestBody @Validated UserEntity userEntity, BindingResult result) {
        userEntity.setId(null);

        /**
         * @link { https://blog.csdn.net/Onstduy/article/details/107901342 }
         * 雪花算法
         * 默认workid也不会重复 String name = ManagementFactory.getRuntimeMXBean().getName();
         */
        IdWorker.initSequence(1, 1);
        long id = IdWorker.getId(userEntity);

        userEntity.setName("userId01");
        userEntity.setAge(10);
        userEntity.setEmail("www.hello.163.com");
        userDao.insert(userEntity);

        int i = 1 / 0;
        userEntity.setEmail("prefix." + userEntity.getEmail());
        userDao.updateById(userEntity);
        return ApiResponse.success(ImmutableMap.of("id", userEntity.getId()));
    }

}
