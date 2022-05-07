package com.wykj.springboot.cfg.webfilter.filter;

import com.wykj.springboot.cfg.webfilter.FilterInterceptorCfg;

import javax.servlet.*;
import java.io.IOException;
import java.util.Date;

//自定义Filter 自动注入SpringBean，由SpringBean管理

/**
 * {@link FilterInterceptorCfg}
 * 注册filter和interceptor
 */
public class MyComponentFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("MyComponentFilter init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("start MyComponentFilter " + new Date());
        filterChain.doFilter(servletRequest, servletResponse);
        System.out.println("end MyComponentFilter " + new Date());
    }

    @Override
    public void destroy() {
        System.out.println("MyComponentFilter destroy");
    }
}
