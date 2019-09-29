package com.wykj.springboot.service.StudentManger;

import org.springframework.stereotype.Component;

@Component
public class StudentManger {

    public String getDbStudentName(String id) {
        throw new IllegalStateException("Db not exist");
    }
}
