package com.wykj.springboot.utils.feign;

import com.wykj.springboot.entity.BaseDO;
import feign.Param;
import feign.RequestLine;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import lombok.Data;

public interface OpenFeignSyncService {

     String FEIGN_REQUEST_URL = "http://localhost:9999";

    @RequestLine("GET /feign/{owner}/{repo}/future")
    CompletableFuture<List<Contributor>> futureContributors(@Param("owner") String owner, @Param("repo") String repo);



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
