package com.wykj.springboot.commom;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.test.annotation.IfProfileValue;
import org.springframework.test.annotation.Repeat;
import org.springframework.test.annotation.Timed;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.EnabledIf;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("pro")
@PropertySource("classpath:application-pro.yml")
@ExtendWith(SpringExtension.class)
public class MybaseTest {

    /***自定义内部类，完成自定义属性加载*/
    public class  MyPropertyResource extends org.springframework.core.env.PropertySource {
        Map map = Maps.newHashMap(ImmutableMap.of("test.key1","key1","test.key2","key2"));

        public MyPropertyResource(String name, Object source) {
            super(name, source);
        }


        @Override
        public Object getProperty(String name) {
            return map.get(name);
        }
    }

    @Autowired
    private Environment environment;


    /**
     * 初始化自定义属性
     */
    @Test
    @Before
    public void addMyProperty() {
        StandardEnvironment standardEnvironment = ((StandardEnvironment) environment);
        standardEnvironment.getPropertySources().addFirst(new MyPropertyResource("myPropertyResource", this));
    }


    @Test
    @Profile("pro")
    public void testProfile() {
        System.out.println("environment:" + JSON.toJSONString(environment.getActiveProfiles()));
        System.out.println("environment:student" + JSON.toJSONString(environment.getProperty("student")));
    }

    @Test
    @Timed(millis = 60000)
    public void testTimed() throws InterruptedException {
        Thread.sleep(9);
    }

    @Test
    @Repeat(2)
    public void testRepeat() {
        System.out.println("repeat");
    }


    /**
     * IfProfileValue 如果没有配置 ProfileValueSourceConfiguration ，默认使用SystemProfileValueSource获取值
     */
    @Test
    @IfProfileValue(name="user.country",value = "CN")
    public void testProfileValue() {
        StandardEnvironment standardEnvironment = ((StandardEnvironment) environment);
        standardEnvironment.getPropertySources().forEach(info->{
            System.out.println(info.getName());
        });
        System.out.println("environment:user.country" + JSON.toJSONString(environment.getProperty("user.country")));
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
