package com.wykj.springboot.cfg.webfilter;

import com.wykj.springboot.cfg.webfilter.filter.MyComponentFilter;
import com.wykj.springboot.cfg.webfilter.interceptor.MyInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName:
 * @Description:
 * @auther: yant09
 * @date: 2019/1/29 22:03
 * 重写springMVC 配置x
 */
@Configuration
public class MySpringMvcConfiguration extends WebMvcConfigurationSupport {
    @Autowired
    private MyInterceptor myInterceptor;
    @Autowired
    private MyComponentFilter componentFilter;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("").setViewName("");
    }

//    自定义拦截器MyInterceptor 注册
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
       registry.addInterceptor(myInterceptor);
    }


    //自定义Filter 需要手动注册Filter    相当于定义web.xml
    @Bean
    public FilterRegistrationBean myFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(componentFilter);

        List<String> urls = new ArrayList<>();
        urls.add("/*");
        filterRegistrationBean.setUrlPatterns(urls);
        return filterRegistrationBean;
    }
}
