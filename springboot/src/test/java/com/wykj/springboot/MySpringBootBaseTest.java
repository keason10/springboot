package com.wykj.springboot;

import com.alibaba.fastjson.JSON;
import com.wykj.springboot.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MySpringBootBaseTest {
    @Autowired
    public MockMvc mvc;
    @Autowired
    private StudentService studentService;

    @Test
    public void testMyControllerStudnet() throws Exception{
        String retrunStr =
                mvc.perform(get("/ctrl/getStudent").contentType(MediaType.APPLICATION_JSON_UTF8).param("str", "string1001"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.id").value(1001))
                        .andReturn().getResponse().getContentAsString();
        System.out.println(retrunStr);

        retrunStr =
                mvc.perform(get("/ctrl/getStudentBase").contentType(MediaType.APPLICATION_JSON_UTF8).param("str", "string1001"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.id").value(1001))
                        .andReturn().getResponse().getContentAsString();
        log.error("{} {}","getStudentBase",retrunStr);

        retrunStr =
                mvc.perform(get("/ctrl/getStudentAll").contentType(MediaType.APPLICATION_JSON_UTF8).param("str", "string1001"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.id").value(1001))
                        .andReturn().getResponse().getContentAsString();
        log.error("{} {}","getStudentAll",retrunStr);
    }

    @Test
    public void testMyControllerAddStudnet() throws Exception{
        studentService.getStudent();
        Map map = new HashMap<>();
        map.put("id", 123);
        map.put("name", "");
        map.put("birthday",LocalDateTime.now().plusDays(1));
        String retrunStr =
                mvc.perform(post("/ctrl/addStudent").contentType(MediaType.APPLICATION_JSON_UTF8).content(JSON.toJSONString(map)))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.id").value(123))
                        .andReturn().getResponse().getContentAsString();
        System.out.println(retrunStr);

        retrunStr =
                mvc.perform(post("/ctrl/addStudentWithoutBindingResult").contentType(MediaType.APPLICATION_JSON_UTF8).content(JSON.toJSONString(map)))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.id").value(123))
                        .andReturn().getResponse().getContentAsString();
        System.out.println(retrunStr);
    }
}
