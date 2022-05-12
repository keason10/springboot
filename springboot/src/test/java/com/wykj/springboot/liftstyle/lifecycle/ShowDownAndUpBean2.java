package com.wykj.springboot.liftstyle.lifecycle;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.SmartLifecycle;

@Slf4j
public class ShowDownAndUpBean2 implements SmartLifecycle {
    private volatile boolean running = false;

    @Override
    public void start() {
        log.info("执行 ShowDownAndUpBean2 shutdownAndUp start 参数 ");
        running = true;
    }

    @Override
    public void stop() {
        log.info("执行 ShowDownAndUpBean2 shutdownAndUp stop 参数 ");
        running = false;
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    @Override
    public void stop(Runnable callback) {
        log.info("执行 ShowDownAndUpBean2 shutdownAndUp Runnable callback stop 参数 ");
        SmartLifecycle.super.stop(callback);
        callback.run();
    }

    @Override
    public int getPhase() {
        return 1;
    }
}
