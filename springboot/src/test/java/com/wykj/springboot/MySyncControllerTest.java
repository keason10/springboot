package com.wykj.springboot;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
public class MySyncControllerTest extends MySpringBootBaseTest {

    @Test
    public void getSync() throws Exception {
        log.info("com.wykj.springboot.MySyncControllerTest.getSync start");
        this.mvc.perform(get("/sync/callable").contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }
}
