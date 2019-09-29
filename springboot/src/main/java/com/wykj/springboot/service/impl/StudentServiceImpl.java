package com.wykj.springboot.service.impl;

import com.wykj.springboot.entity.Student;
import com.wykj.springboot.service.StudentManger.StudentManger;
import com.wykj.springboot.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentManger studentManger;

    @Override
    public Student getStudent() {
        return null;
    }

    @Override
    public String getDbStudentName(String id) {
        return studentManger.getDbStudentName(id);
    }

    @Override
    public String getStudentServiceImplInnerName(String id) {
        return null;
    }


    public String getServiceImplInnerName(String id) {
        throw new IllegalStateException("该方法还没被实现");
    }
}
