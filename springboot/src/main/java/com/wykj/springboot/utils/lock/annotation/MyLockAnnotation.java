package com.wykj.springboot.utils.lock.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface MyLockAnnotation {
    String prefix();
    String keyJSONPath();
    int lockSeconds();
}
