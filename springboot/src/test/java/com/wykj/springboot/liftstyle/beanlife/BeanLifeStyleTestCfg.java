package com.wykj.springboot.liftstyle.beanlife;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;

@Slf4j
public class BeanLifeStyleTestCfg {

    @Bean(initMethod = "initMethod", destroyMethod = "destroyMethod")
    public BeanLifeStyleTest beanLifeStyleTest() {
        log.info("执行 BeanLifeStyleTestCfg beanLifeStyleTest 参数 ");
        return new BeanLifeStyleTest();
    }

    @Bean
    public BeanFactoryPostProcessorLifeCycle beanFactoryPostProcessorLifeCycle() {
        return new BeanFactoryPostProcessorLifeCycle();
    }


    @Bean
    public BeanPostProcessorLifeCycle beanPostProcessorLifeCycle() {
        return new BeanPostProcessorLifeCycle();
    }
}
