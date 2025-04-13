package com.example.demoback.web;

import com.example.demoback.entity.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
public class indexAction {

    @RequestMapping("hello")
    public String hello(String name){
        return "hello "+name;
    }

    @Value("${upload.diskPath}")
    String diskPath;
    @Value("${upload.webPath}")
    String webPath;

    @RequestMapping("upload")
    public Result upload(@RequestParam("file") MultipartFile uploadFile) throws IOException {
        String filename = uploadFile.getOriginalFilename();
        int index = filename.lastIndexOf(".");
        String suffix = index == -1 ? "" : filename.substring(index);
        String perfix = UUID.randomUUID().toString().replaceAll("-","");
        String newFileName = perfix + suffix;
        File diskFile = new File(diskPath + newFileName);
        uploadFile.transferTo(diskFile);
        String webFile =  "http://127.0.0.1:9527" + webPath + newFileName;
        return new Result(1,"success",webFile);
    }

}
