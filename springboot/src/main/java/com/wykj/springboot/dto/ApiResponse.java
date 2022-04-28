package com.wykj.springboot.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.ImmutableMap;
import java.io.Serializable;
import lombok.Data;

@Data
public class ApiResponse<T extends Serializable> implements Serializable {

    private Integer reCode;
    private String reMsg;
    private T data;


    public ApiResponse(Integer reCode, String reMsg, T data) {
        this.reCode = reCode;
        this.reMsg = reMsg;
        this.data = data;
    }

    public ApiResponse(Integer reCode, String reMsg) {
        this.reCode = reCode;
        this.reMsg = reMsg;
    }

    public static <T extends Serializable> ApiResponse<T> success(T data) {
        return new ApiResponse(0, "成功", data == null ? ImmutableMap.of() : data);
    }

    public static <T extends Serializable> ApiResponse<T> error(String reMsg) {
        return new ApiResponse(-1, reMsg);
    }

    public boolean isOk() {
        return Integer.valueOf(0).equals(reCode);
    }

    @JsonIgnore
    public boolean isNotOk() {
        return !Integer.valueOf(0).equals(reCode);
    }

    public ApiResponse() {
    }





}
