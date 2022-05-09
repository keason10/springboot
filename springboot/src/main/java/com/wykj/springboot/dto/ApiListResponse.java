package com.wykj.springboot.dto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiListResponse<T extends Serializable> implements Serializable {
    private String reMsg;
    private PageDTO<T> data;
    private Integer reCode;

    public ApiListResponse(Integer reCode, String reMsg, PageDTO<T> pageDTO) {
        this.reCode = reCode;
        this.reMsg = reMsg;
        this.data = pageDTO;
    }

    public ApiListResponse(Integer reCode, String reMsg) {
        this.reCode = reCode;
        this.reMsg = reMsg;
    }

    public static <T extends Serializable> ApiListResponse<T> success(PageDTO<T> pageDTO) {
        return new ApiListResponse(0, "成功", pageDTO == null ? new PageDTO() : pageDTO);
    }

    public static <T extends Serializable> ApiListResponse<T> error(String reMsg) {
        return new ApiListResponse<>(-1, reMsg);
    }

    public boolean isOk() {
        return Integer.valueOf(0).equals(reCode);
    }

    @JsonIgnore
    public boolean isNotOk() {
        return !Integer.valueOf(0).equals(reCode);
    }

    public ApiListResponse() {
    }
}
