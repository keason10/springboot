package com.wykj.springboot.utils.feign;

import com.wykj.springboot.dto.ApiListResponse;
import com.wykj.springboot.entity.BaseDO;
import com.wykj.springboot.entity.UserEntity;
import feign.Param;
import feign.RequestLine;
import java.util.List;
import lombok.Data;

public interface OpenFeignService {

     String FEIGN_REQUEST_URL = "http://localhost:9999";

    /**
     * 说明
     *      默认把Issue当成Body传递
     * @param userEntity
     * @param owner
     * @param repo
     */
    @RequestLine("POST /feign/{owner}/{repo}/issues")
    ApiListResponse<UserEntity> createUser(UserEntity userEntity, @Param("owner") String owner, @Param("repo") String repo);


    @RequestLine("GET /feign/{owner}/{repo}/contributors")
    List<Contributor> contributors(@Param("owner") String owner, @Param("repo") String repo);


    @Data
    class Repository {
        String name;
    }

    @Data
    class Contributor extends BaseDO {
        String login;
    }

    class Issue {

        Issue() {

        }

        String title;
        String body;
        List<String> assignees;
        int milestone;
        List<String> labels;
    }

}
