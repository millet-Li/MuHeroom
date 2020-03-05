package com.room.controller;


import com.room.mapper.UserMapper;
import com.room.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class FileUploadController {

    private final ResourceLoader resourceLoader;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    public FileUploadController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Value("${mydata.upload-patha}")
    private String localPath;

    //    上传头像
    @RequestMapping("/imageUpload")
    public String upload(@RequestParam("file") MultipartFile file,
                         @RequestParam("username") String username) {
        //1定义要上传文件 的存放路径
//        String localPath="D:/room/image";
        //2获得文件名字
        String fileName = file.getOriginalFilename();
//        System.out.println(fileName);
        //3保存图片到本地，并返回可访问的地址
        String sqlPath = "false";
        int num = 1;
        sqlPath = FileUtils.upload(file, localPath, fileName, num);
        //4图像路径存入数据库
        if (!sqlPath.equals("false")) {
//            System.out.println("上传成功，图片路径是："+sqlPath);
            userMapper.updateImages(username, sqlPath);
        } else {
            System.out.println("上传头像失败！");
        }
        return "redirect:/room?author=" + username;
    }
}
