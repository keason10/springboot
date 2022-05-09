package com.wykj.springboot.cfg;

public interface ZApplicationStaticConfig {

    // 异常全局捕获扫描路径
    String  CONTROLLER_ADVICE_SCAN_PACKAGE = "com.wykj.springboot";

    // 异常全局捕获扫描路径
    String  SWAGGER_SCAN_PACKAGE = "com.wykj.springboot";

    // mybatis plus dao 扫描路径
    String MYBATIS_PLUS_MAPPER_SCAN = "com.wykj.springboot.dao";
}
