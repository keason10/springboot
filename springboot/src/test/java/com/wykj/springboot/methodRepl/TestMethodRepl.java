package com.wykj.springboot.methodRepl;

import com.wykj.springboot.typeEditor.AconfigExoticTypeCfg;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

@Import({AconfigExoticTypeCfg.class})
@ImportResource(locations = {"classpath:**/methodReplace.xml"})
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TestMethodRepl{
    @Autowired
    private MyValueCalculator myValueCalculator;

    @Test
    public void typeEditorTest() {
        myValueCalculator.computeValue("124");
        System.out.println("exoticType");
        ConfigurableApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");

        // add a shutdown hook for the above context...
        ctx.registerShutdownHook();

    }
}

