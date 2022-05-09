package com.wykj.springboot.controller;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.wykj.springboot.dto.ApiListResponse;
import com.wykj.springboot.dto.PageDTO;
import com.wykj.springboot.entity.UserEntity;
import com.wykj.springboot.utils.feign.OpenFeignSyncService;
import com.wykj.springboot.utils.feign.OpenFeignService;
import com.wykj.springboot.utils.feign.OpenFeignService.Contributor;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import javax.annotation.Resource;
import javax.jnlp.UnavailableServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping(path = "/feign")
public class MyFeignTestController {

    @Autowired
    OpenFeignService openFeignTestService;

    @Autowired
    private ObjectMapper mapper;

    @Resource
    @Qualifier("openFeignSyncTestService")
    OpenFeignSyncService openFeignSyncTestService;

    @GetMapping(path = "/{owner}/{repo}/contributors")
    @ResponseBody
    public List<Contributor> contributors(
            @PathVariable("owner") String owner,
            @PathVariable("repo") String repo
    ) {
        log.info("执行 MyFeignTestController contributors 参数");
        new Thread(() -> {
            log.info("执行 MyFeignTestController contributors inner");
        }).start();
        return Arrays.asList(new Contributor());
    }


    @GetMapping(path = "/{owner}/{repo}/future")
    @ResponseBody
    public CompletableFuture<List<Contributor>> contributorsFuture(
            @PathVariable("owner") String owner,
            @PathVariable("repo") String repo
    ) throws ExecutionException, InterruptedException {
        log.info("执行 MyFeignTestController contributorsFuture 参数");
        CompletableFuture<List<Contributor>> listCompletableFuture = CompletableFuture.supplyAsync(() -> {
            log.info("执行 MyFeignTestController contributorsFuture inner");
            return Arrays.asList(new Contributor());
        });
        return listCompletableFuture;
    }


    @PostMapping(path = "/{owner}/{repo}/issues")
    @ResponseBody
    public ApiListResponse<UserEntity> createUserEntity(@RequestBody UserEntity userEntity,
            @PathVariable("owner") String owner,
            @PathVariable("repo") String repo
    ) {
        log.info("执行 MyFeignTestController createUserEntity 参数 {}", JSON.toJSONString(userEntity));
        return ApiListResponse.success(new PageDTO<>(1l, Arrays.asList(userEntity)));
    }


    @PostMapping(path = "trigger")
    @ResponseBody
    public<T> Object trigger() throws ExecutionException, InterruptedException, JsonProcessingException {
        UserEntity entity = new UserEntity();
        entity.setId(121323L);
        entity.setName("hello");
        entity.setAge(12);
        entity.setEmail("hlee.maer.com");
        log.info("执行 MyFeignTestController trigger 参数 ");


        return  openFeignTestService.createUser(entity, "ownerStr", "repoStr");
        // return openFeignTestService.contributors("ownerStr", "repoStr");

        // CompletableFuture<List<OpenFeignSyncTestService.Contributor>> listCompletableFuture = openFeignSyncTestService.futureContributors(
        //         "ownerStr",
        //         "repoStr");
        // return listCompletableFuture.get();
    }

}
