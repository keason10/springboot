package com.wykj.springboot.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

//文件上传
@RestController
@RequestMapping(path= "upload")
public class FileUploadController {

    @RequestMapping(path = "saveFile" ,method = RequestMethod.POST)
    @ResponseBody
    //文件的话只需要一个变量即，多文件上传的话就将MultipartFile改为数组，然后分别上传保存即可
    //注意@RequestParam("file") 变量file 不能修改，是按照file 变量自动识别的
    public String uploadFile(@RequestParam("file") MultipartFile[] files) {
        String fileName = null;
        String msg = "";
        if (files != null && files.length > 0) {
            for (int i = 0; i < files.length; i++) {
                try {
                    fileName = files[i].getOriginalFilename();
                    byte[] bytes = files[i].getBytes();
                    BufferedOutputStream buffStream =
                            new BufferedOutputStream(new FileOutputStream(new File("/tmp/" + fileName)));
                    buffStream.write(bytes);
                    buffStream.close();
                    msg += "You have successfully uploaded " + fileName;
                } catch (Exception e) {
                    return "You failed to upload " + fileName + ": " + e.getMessage();
                }
            }
            return msg;
        }
        return "message";
    }

}


