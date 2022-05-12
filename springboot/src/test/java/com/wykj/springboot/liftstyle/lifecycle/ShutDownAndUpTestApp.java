package com.wykj.springboot.liftstyle.lifecycle;


import cn.hutool.core.thread.ThreadUtil;
import com.wykj.springboot.commom.MySpringBootBaseTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.support.GenericWebApplicationContext;

@Slf4j
@Import({ShowDownAndUpCfg.class})
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ShutDownAndUpTestApp extends MySpringBootBaseTest {
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private ShowDownAndUpBean showDownAndUpBean;

    @Test
    public  void showDownAndUpBean() {
        GenericWebApplicationContext applicationContext = (GenericWebApplicationContext) this.applicationContext;
        showDownAndUpBean.showKey();
        applicationContext.stop();
        ThreadUtil.sleep(1000);
        applicationContext.start();
        showDownAndUpBean.showKey();
        ThreadUtil.sleep(1000);
        log.info("执行 ShowDownAndUpBean shutAndUp 参数 ");
    }

    @Test
    public  void showDownAndUpBean2() {
        GenericWebApplicationContext applicationContext = (GenericWebApplicationContext) this.applicationContext;
        ThreadUtil.sleep(1000);
        applicationContext.stop();
        ThreadUtil.sleep(1000);
        log.info("执行 ShowDownAndUpBean2 shutdownAndUp 参数 ");
    }
}
