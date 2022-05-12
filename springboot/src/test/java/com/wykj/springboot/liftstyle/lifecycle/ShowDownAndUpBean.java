package com.wykj.springboot.liftstyle.lifecycle;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.Lifecycle;

@Slf4j
public class ShowDownAndUpBean implements Lifecycle {

    private volatile boolean running = false;
    @Value("${test.key:default}")
    private String key;

    public void showKey() {
        log.info("执行 ShowDownAndUpBean2 showKey 参数 key {}", key);
    }

    @Override
    public void start() {
        running = true;
        log.info("执行 ShowDownAndUpBean shutAndUp start 参数 ");
    }

    @Override
    public void stop() {
        running = false;
        log.info("执行 ShowDownAndUpBean shutAndUp stop 参数 ");
    }

    @Override
    public boolean isRunning() {
        return running;
    }
}
