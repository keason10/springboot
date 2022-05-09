package com.wykj.springboot.utils.lock.aspect;

import cn.hutool.core.util.ArrayUtil;
import com.alibaba.fastjson.JSON;
import com.jayway.jsonpath.JsonPath;
import com.wykj.springboot.utils.lock.annotation.MyLockAnnotation;
import com.wykj.springboot.utils.lock.redis.RedisReadWriteLock;
import java.lang.reflect.Method;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@Aspect
@Component
@Slf4j
@Order(Integer.MIN_VALUE + 1)
public class MyLockAspect {

    @Around("@annotation(com.wykj.springboot.utils.lock.annotation.MyLockAnnotation)")
    public Object handleLockAnnotation(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        log.info("执行 MyLockAspect handleLockAnnotation start");
        Object[] objects = proceedingJoinPoint.getArgs();

        if (ArrayUtil.isEmpty(objects)) {
            throw new RuntimeException("MyLockAnnotation标注的方法第一个参数必须存在");
        }
        //判断方法是否有注解
        Signature signature = proceedingJoinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method targetMethod = methodSignature.getMethod();
        MyLockAnnotation lockAnnotation = targetMethod.getAnnotation(MyLockAnnotation.class);
        String keyJSONPath = lockAnnotation.keyJSONPath();
        Object businessKey = null;
        try {
            businessKey = JsonPath.read(JSON.toJSONString(objects[0]), keyJSONPath);
            if (businessKey == null) {
                throw new RuntimeException(String.format("MyLockAspect 根据第一个参数的[%s]属性获得的businessKey为空", keyJSONPath));
            }
        } catch (RuntimeException e) {
            log.error("执行 MyLockAspect handleLockAnnotation 异常", e);
            throw new RuntimeException(String.format("MyLockAspect 根据第一个参数的[%s]属性获取businessKey异常", keyJSONPath));
        }
        String prefix = lockAnnotation.prefix();
        int lockSeconds = lockAnnotation.lockSeconds();
        RedisReadWriteLock redisReadWriteLock = new RedisReadWriteLock(prefix + businessKey, lockSeconds, 1);
        if (redisReadWriteLock.tryWLock()) {
            try {
                Object object = proceedingJoinPoint.proceed();
                return object;
            } catch (Throwable e) {
                throw e;
            }finally {
                redisReadWriteLock.wUnlock();
            }
        } else {
            throw new RuntimeException(String.format("MyLockAspect businessKey %s 获取锁失败", businessKey));
        }
    }
}

