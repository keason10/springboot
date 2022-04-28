package com.wykj.springboot.cfg.advice;

import com.alibaba.fastjson.JSON;
import com.wykj.springboot.dto.ApiResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice(basePackages = {"com.wykj.springboot.controller"})
public class MyExceptionAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<?> handleControllerException(HttpServletRequest request, Throwable ex) {
        HttpStatus status = getStatus(request);
        Map<String, String> messages = new HashMap<>();
        if (ex instanceof BindException) {
            BindingResult result = ((BindException) ex).getBindingResult();
            if (result.hasErrors()) {
                List<ObjectError> errors = result.getAllErrors();
                for (ObjectError error : errors) {
                    FieldError fieldError = (FieldError) error;
                    messages.put(fieldError.getField(), fieldError.getDefaultMessage());
                }
            }
            return new ResponseEntity<ApiResponse>(ApiResponse.error(JSON.toJSONString(messages)), status);
        } else {
            return new ResponseEntity<ApiResponse>(ApiResponse.error(ex.getMessage()), status);
        }
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}
