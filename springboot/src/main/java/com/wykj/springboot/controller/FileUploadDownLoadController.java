package com.wykj.springboot.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

//文件上传
@Slf4j
@RestController
@RequestMapping(path= "file")
public class FileUploadDownLoadController {
    private static String folder = "C:\\Users\\lenovo\\IdeaProjects\\springboot\\springboot\\";

    @PostMapping("upload")
    //文件的话只需要一个变量即，多文件上传的话就将MultipartFile改为数组，然后分别上传保存即可
    //注意@RequestParam("file") 变量file 不能修改，是按照file 变量自动识别的
    public String uploadFile(@RequestParam("file") MultipartFile[] files) {
        String fileName = null;
        String msg = "";
        if (files != null && files.length > 0) {
            for (int i = 0; i < files.length; i++) {
                try {
//                      第一种方式保存问题件
//                    fileName = files[i].getOriginalFilename();
//                    String fileNameStr = folder + System.nanoTime() + ".txt";
//                    byte[] bytes = files[i].getBytes();
//                    BufferedOutputStream buffStream =
//                            //进过测试默认上传到了 C:\tmp 目录之下
//                            new BufferedOutputStream(new FileOutputStream(new File(fileNameStr)));
//                    buffStream.write(bytes);
//                    buffStream.close();

//                    第二种方式保存文件
                    String fileNameStr = folder + System.nanoTime() + ".txt";
                    File localFile = new File(fileNameStr);
                    files[i].transferTo(localFile);
                    msg += "You have successfully uploaded " + localFile.getAbsolutePath();
                    System.out.println(msg);
                } catch (Exception e) {
                    log.error("com.wykj.springboot.controller.FileUploadDownLoadController.uploadFile error", e);
                }
            }
            return msg;
        }
        return "message";
    }

    @GetMapping("/{id}")
    public void download(@PathVariable("id") String id, HttpServletRequest request, HttpServletResponse response) {
        try( FileInputStream inputStream = new FileInputStream(folder + id + ".txt");
             OutputStream outputStream = response.getOutputStream();) {
            response.setContentType("application/x-download");
            response.addHeader("Content-Disposition","attachment;filename=test.txt");
            IOUtils.copy(inputStream, outputStream);
        } catch (IOException e) {
            log.error("com/wykj/springboot/controller/FileUploadDownLoadController.download error", e);
        }
    }

}


