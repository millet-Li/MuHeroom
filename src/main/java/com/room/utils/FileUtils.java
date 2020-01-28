package com.room.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileUtils {

    public static String upload(MultipartFile file, String path, String fileName,int num){

        // UUID生成新的文件名
//        String realPath = path + "/" + FileNameUtils.getFileName(fileName);

        //使用原文件名
//         String realPath = path + "/" + fileName;
        if (fileName.equals(""))
            return "false";

        //使用时间戳命名文件
         String fName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "_" + fileName;
         String realPath = path + "/" + fName;
        String sqlPath;
         if (num == 1){
             sqlPath = "image/" + fName;
         }else {
             sqlPath = "life/" + fName;
         }


//         System.out.println(realPath);
//         System.out.println(sqlPath);
        File dest = new File(realPath);

        //判断文件父目录是否存在
        if(!dest.getParentFile().exists()){
//            System.out.println("No OK");
            dest.getParentFile().mkdir();
        }

        try {
            //保存文件
            file.transferTo(dest);
            return sqlPath;
//            return true;
        } catch (IllegalStateException e) {
            e.printStackTrace();
            return "false";
        } catch (IOException e) {
            e.printStackTrace();
            return "false";
        }

    }
}

