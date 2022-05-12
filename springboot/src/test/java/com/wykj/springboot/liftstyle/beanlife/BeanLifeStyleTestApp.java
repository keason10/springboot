package com.wykj.springboot.liftstyle.beanlife;


import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@Import(BeanLifeStyleTestCfg.class)
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BeanLifeStyleTestApp  {

    @Autowired
    private BeanLifeStyleTest beanLifeStyleTest;

    @Test
    public void testLifeStyle() {
        beanLifeStyleTest.show();
        log.info("执行 BeanLifeStyleTestApp testLifeStyle 参数 ");
    }
}
