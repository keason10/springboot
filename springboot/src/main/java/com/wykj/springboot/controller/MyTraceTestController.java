package com.wykj.springboot.controller;

import cn.hutool.core.thread.ThreadUtil;
import com.wykj.springboot.dto.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping(path = "/trace")
public class MyTraceTestController {
    @PostMapping(path = "/test")
    @ResponseBody
    public ApiResponse<String> testTraceId() {
        log.info("执行 MyLockController testTraceId Test Main Thread");
        new Thread(() -> {
            log.info("执行 MyLockController testTraceId subThread");
        }).start();
        return ApiResponse.success("查看日志看TradeId");
    }
}
