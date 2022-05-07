package com.wykj.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@TableName("user")
@Data
@ApiModel(value = "用户信息", description = "用户信息")
public class UserEntity {
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;
    @ApiModelProperty(value = "用户名",name = "name",example = "张三")
    private String name;
    
    @ApiModelProperty(value = "年龄",name = "age",example = "12")
    private Integer age;

    @ApiModelProperty(value = "邮件",name = "email",example = "hello.163.com")
    private String email;
}
