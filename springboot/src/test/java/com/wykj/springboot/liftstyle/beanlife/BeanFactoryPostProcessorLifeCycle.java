package com.wykj.springboot.liftstyle.beanlife;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

@Slf4j
public class BeanFactoryPostProcessorLifeCycle implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory)
            throws BeansException {
        log.info("执行 BeanFactoryPostProcessorLifeCycle postProcessBeanFactory 参数");
    }
}
