package com.wykj.springboot.cfg.webfilter.interceptor;

import com.wykj.springboot.cfg.annotation.MyMethodAnnotation;
import com.wykj.springboot.cfg.FilterInterceptorCfg;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * @ClassName:
 * @Description:
 * @auther: yant09
 * @date: 2019/1/29 22:25
 *         拦截器配置
 *         {@link FilterInterceptorCfg}
 *         注册filter和interceptor
 */
// 自定义拦截器MyInterceptor
public class MyInterceptor implements HandlerInterceptor {

    //    preHandle 返回false ,就不会执行Controller 方法
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        System.out.println("MyInterceptor preHandle");;
        request.setAttribute("startTime", new Date().getTime());

        //判断方法是否有注解 如果没有注解返回null,  如果有注解返回声明的相应注解
        // MyMethodAnnotation myMethodAnnotation = ((HandlerMethod) handler).getMethodAnnotation(MyMethodAnnotation.class);
        // PostMapping postMapping = ((HandlerMethod) handler).getMethodAnnotation(PostMapping.class);
        return true;
    }

    //    如果Controller 抛出异常，postHandle 就不会执行
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        System.out.println("MyInterceptor postHandle");
        System.out.println("startTime " + request.getAttribute("startTime"));
    }

    //不管是否抛异常，afterCompletion 都会被调用
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        System.out.println("MyInterceptor afterCompletion");
    }

}
