package com.wykj.springboot;

import static com.wykj.springboot.cfg.ZApplicationStaticConfig.MYBATIS_PLUS_MAPPER_SCAN;

import com.wykj.springboot.cfg.Swagger2AndWebCfg;
import com.wykj.springboot.cfg.webfilter.FilterInterceptorCfg;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableScheduling
@EnableAspectJAutoProxy
@EnableTransactionManagement
@MapperScan(MYBATIS_PLUS_MAPPER_SCAN)
@Import({FilterInterceptorCfg.class, Swagger2AndWebCfg.class})
public class SpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootApplication.class, args);
    }
}
