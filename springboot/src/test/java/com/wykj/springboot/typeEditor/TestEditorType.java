package com.wykj.springboot.typeEditor;

import com.wykj.springboot.commom.MySpringBootBaseTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.junit4.SpringRunner;

@Import({AconfigExoticTypeCfg.class})
@ImportResource(locations = {"classpath:typeEditor/ExoticType.xml"})
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TestEditorType extends MySpringBootBaseTest {
    @Autowired
    private DependsOnExoticType exoticType;

    @Test
    public void typeEditorTest() {
        System.out.println(exoticType);
    }
}
