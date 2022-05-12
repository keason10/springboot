package com.wykj.springboot.liftstyle.beanlife;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.lang.Nullable;

@Slf4j
public class BeanPostProcessorLifeCycle implements BeanPostProcessor {


    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        log.info("执行 BeanPostProcessorLifeCycle postProcessBeforeInitialization 参数  {}", beanName);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        log.info("执行 BeanPostProcessorLifeCycle postProcessAfterInitialization 参数 {}", beanName);
        return bean;
    }
}
