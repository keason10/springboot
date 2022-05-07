package com.wykj.springboot.controller;

import com.wykj.springboot.dto.ApiResponse;
import com.wykj.springboot.entity.UserEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping(path = "/trace")
@Api(value = "traceId和Swagger测试", tags = {"traceId和Swagger测试"})
public class MyTraceSwaggerController {
    @PostMapping(path = "/test")
    @ResponseBody
    @ApiOperation(value = "测试traceId", notes = "测试traceId")
    public ApiResponse<String> testTraceId(@RequestBody UserEntity userEntity) {
        log.info("执行 MyLockController testTraceId Test Main Thread");
        new Thread(() -> {
            log.info("执行 MyLockController testTraceId subThread");
        }).start();
        return ApiResponse.success("查看日志看TradeId");
    }
}
