package com.wykj.springboot.entity;

import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;


@Component
@ConfigurationProperties(prefix = "student")
@Data
public class Student implements Serializable {
    public interface StudentBase {};
    public interface StudentAll extends StudentBase{};

   @JsonView(StudentBase.class)
    private Integer id;

    @JsonView(StudentBase.class)
    @NotBlank(message ="姓名不能为空")
    private String name;

    @JsonView(StudentBase.class)
    private Integer age;

    @JsonView(StudentBase.class)
    private String sexStr;

    @JsonView(StudentAll.class)
    @Past
    private Date birthday;

    @JsonView(StudentAll.class)
    private Map map;
}
