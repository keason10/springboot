package com.wykj.springboot.aspect;

import com.wykj.springboot.annotation.MyMethodAnnotation;
import com.wykj.springboot.entity.Student;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;


@Aspect
@Component
@Slf4j
public class MyControllerAspect {
    @Around("execution(* com.wykj.springboot.controller.MyController.*(..))")
    public Object handleControllerMethod(ProceedingJoinPoint proceedingJoinPoint)throws Throwable {
        log.warn("com.wykj.springboot.aspect.MyPointCut.handleControllerMethod start");
        Object[] objects = proceedingJoinPoint.getArgs();


        //判断方法是否有注解
        Signature signature = proceedingJoinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature)signature;
        Method targetMethod = methodSignature.getMethod();
        boolean exist =targetMethod.isAnnotationPresent(MyMethodAnnotation.class);


        for (int i = 0; i < objects.length; i++) {
            log.warn("objects[{}] = {}", i, objects[i]);
        }
        Object object = proceedingJoinPoint.proceed();
        log.warn("com.wykj.springboot.aspect.MyPointCut.handleControllerMethod end");
        return object;
    }
}

