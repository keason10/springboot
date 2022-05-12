package com.wykj.springboot.liftstyle.beanlife;


import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.SmartLifecycle;

@Slf4j
public class BeanLifeStyleTest implements InitializingBean, DisposableBean{
    private volatile boolean running = false;

    public void show() {
        log.info("执行 BeanLifeStyleTest show 参数 ");
    }

    public void initMethod() {
        log.info("执行 BeanLifeStyleTest initMethod 参数 ");
    }

    public void destroyMethod() {
        log.info("执行 BeanLifeStyleTest destroyMethod 参数 ");
    }


    @PostConstruct
    public void initMethodPost() {
        log.info("执行 BeanLifeStyleTest initMethodPost 参数 ");
    }

    @PreDestroy
    public void destroyMethodPre() {
        log.info("执行 BeanLifeStyleTest destroyMethodPre 参数 ");
    }



    @Override
    public void destroy() throws Exception {
        log.info("执行 BeanLifeStyleTest destroy 参数 ");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("执行 BeanLifeStyleTest afterPropertiesSet 参数 ");
    }

}
