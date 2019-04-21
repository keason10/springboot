package com.wykj.springboot.advice;

import com.sun.deploy.net.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;
@Slf4j
@ControllerAdvice
public class MyExceptionAdvice {
    @ExceptionHandler(Exception.class)
    @ResponseBody
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String,Object> getException(HttpRequest request, HttpResponse response,Exception ex) throws Exception{
        if (AnnotationUtils.findAnnotation(ex.getClass(), ResponseStatus.class) != null) {
            throw ex;
        }
        log.error("发生异常 {}", ex.getMessage(), ex);
        Map map = new HashMap();
        map.put("msg", ex.getMessage());
        return map;
    }
}
