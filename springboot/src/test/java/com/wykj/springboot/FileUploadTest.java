package com.wykj.springboot;

import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FileUploadTest extends MySpringBootBaseTest {

    @Test
    public void testFileUpload() throws Exception {
        this.mvc.perform(fileUpload("/file/upload").
                file(new MockMultipartFile("file", "text.txt", "multipart/form-data", "hello keason".getBytes())))
        .andExpect(status().isOk());
    }
}
