package com.wykj.springboot.service;

import com.wykj.springboot.entity.Student;

public interface StudentService {
    Student getStudent();

    String getDbStudentName(String id);


    String getStudentServiceImplInnerName(String id);
}
