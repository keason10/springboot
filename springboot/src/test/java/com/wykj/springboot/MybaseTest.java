package com.wykj.springboot;

import com.alibaba.fastjson.JSON;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.test.annotation.IfProfileValue;
import org.springframework.test.annotation.Repeat;
import org.springframework.test.annotation.SystemProfileValueSource;
import org.springframework.test.annotation.Timed;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.EnabledIf;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("pro")
@PropertySource("classpath:application-pro.yml")
@ExtendWith(SpringExtension.class)
public class MybaseTest {
    @Autowired
    private Environment environment;
    @Autowired
    private ApplicationContext context;



    @Test
    @Profile("pro")
    public void testProfile() {
        System.out.println("environment:" + JSON.toJSONString(environment.getActiveProfiles()));
        System.out.println("environment:student" + JSON.toJSONString(environment.getProperty("student")));
    }

    @Test
    @Timed(millis = 2000)
    public void testTimed() throws InterruptedException {
        Thread.sleep(9);
    }

    @Test
    @Repeat(2)
    public void testRepeat() {
        System.out.println("repeat");
    }


    @Test
    @IfProfileValue(name="profile",value = "pro")
    public void testProfileValue() {
        System.out.printf("profile dev");
        System.out.println("environment:" + environment.getActiveProfiles());
        System.out.println("environment:student" + JSON.toJSONString(environment.getProperty("student")));
    }

    @org.junit.jupiter.api.Test
    @EnabledIf("#{systemProperties['user.country'].toLowerCase().contains('mac')}")
    public void testEnableIf() {
        System.out.println("testEnableIf true");
    }

    @org.junit.jupiter.api.Test
    @EnabledIf("#{systemProperties['user.country'].toLowerCase().contains('cn')}")
    public void testEnableIfFalse() {
        System.out.println("testEnableIf false");
    }
}
