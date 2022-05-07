package com.wykj.springboot.utils.trace;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

//自定义Filter 自动注入SpringBean，由SpringBean管理

/**
 * {@link com.wykj.springboot.cfg.webfilter.MySpringMvcConfiguration}
 * 注册filter和interceptor
 */
@Component
@Slf4j
public class TraceWebFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            HttpServletRequest request = (HttpServletRequest) servletRequest;

            //获取trace id
            String traceId = request.getHeader(TraceHttpHeader.HTTP_HEADER_TRACE_ID.getCode());
            TraceContextHolder.setCurrentTrace(traceId);
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (Exception e) {
            log.error("执行 TraceWebFilter doFilter 异常", e);
        }finally {
            TraceContextHolder.removeContext();
        }

    }

    @Override
    public void destroy() {
    }
}
