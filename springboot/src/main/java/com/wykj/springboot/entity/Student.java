package com.wykj.springboot.entity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.util.Date;
import java.util.Map;


@Component
@ConfigurationProperties(prefix = "student")
@Data
public class Student {
    private Integer id;
    @NotBlank(message ="姓名不能为空")
    private String name;
    private Integer age;
    private String sexStr;
    @Past
    private Date birthday;
    private Map map;
}
