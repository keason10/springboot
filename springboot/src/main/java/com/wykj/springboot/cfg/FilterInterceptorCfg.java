package com.wykj.springboot.cfg;

import com.wykj.springboot.cfg.webfilter.filter.MyComponentFilter;
import com.wykj.springboot.cfg.webfilter.interceptor.MyInterceptor;
import com.wykj.springboot.entity.Student;
import com.wykj.springboot.entity.UserEntity;
import com.wykj.springboot.utils.trace.TraceWebFilter;
import java.util.ArrayList;
import java.util.List;
import org.apache.catalina.User;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.theme.ThemeChangeInterceptor;

/**
 * @ClassName:
 * @Description:
 * @auther: yant09
 * @date: 2019/1/29 22:03 重写springMVC 配置x
 *         参考{@link  org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration}
 *         SpringBoot做了这个限制，只有当WebMvcConfigurationSupport类不存在的时候才会生效WebMvc自动化配置，
 *         WebMvc自动配置类中不仅定义了classpath:/META-INF/resources/，classpath:/resources/，classpath:/static/，classpath:/public/等路径的映射，
 *         还定义了配置文件spring.mvc开头的配置信息等
 */
@Configuration
@EnableWebMvc
public class FilterInterceptorCfg implements WebMvcConfigurer {

    /**
     * 从类上的说明得到，此处的addInterceptors 不生效，因为 Swagger2AndWebCfg 继承类 WebMvcConfigurationSupport
     * 造成 这个类的 @EnableWebMvc 不生效，所以这个类配置不了 拦截器，只能在 Swagger2AndWebCfg 中的 public void addInterceptors(InterceptorRegistry
     * registry) 配置拦截器
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LocaleChangeInterceptor());
        registry.addInterceptor(new ThemeChangeInterceptor()).addPathPatterns("/**").excludePathPatterns("/admin/**");
        registry.addInterceptor(new MyInterceptor());
    }

    @Bean
    public MyComponentFilter myComponentFilter() {
        return new MyComponentFilter();
    }

    @Bean
    public TraceWebFilter traceWebFilter() {
        return new TraceWebFilter();
    }

    /**
     * A @Bean-annotated method can have an arbitrary number of parameters that describe the dependencies required to
     * build that bean.
     * For instance, if our TransferService requires an AccountRepository, we can materialize that dependency with a
     * method parameter,
     * as the following example shows:
     *
     *  执行如下的代码，了解依赖注入的值
     *      {@link com.wykj.springboot.springbcallback.MyCommandLineRunner}
     *      com/wykj/springboot/springbcallback/MyCommandLineRunner.java:25
     */
    @Bean("userEntitySingle")
    public Student user(UserEntity user) {
        Student student = new Student();
        student.setUserEntity(user);
        return student;
    }
    @Bean
    public UserEntity userEntity() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(11212L);
        userEntity.setName("name111");
        userEntity.setAge(12);
        userEntity.setEmail("tx.163.com");
        return userEntity;
    }


    /**
     * 自定义Filter 需要手动注册Filter    相当于定义web.xml
     * Order 数字越小执行越早
     *
     * @return
     */

    @Bean
    public FilterRegistrationBean myFilter1() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(traceWebFilter());
        filterRegistrationBean.setOrder(1);
        ;
        return filterRegistrationBean;
    }

    //自定义Filter 需要手动注册Filter    相当于定义web.xml
    @Bean
    public FilterRegistrationBean myFilter2() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(myComponentFilter());
        filterRegistrationBean.setOrder(2);
        List<String> urls = new ArrayList<>();
        urls.add("/*");
        filterRegistrationBean.setUrlPatterns(urls);
        return filterRegistrationBean;
    }
}
