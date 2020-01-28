package com.room.controller;

import com.alibaba.fastjson.JSON;
import com.room.dto.Lifes;
import com.room.entity.Life;
import com.room.mapper.LifeMapper;
import com.room.mapper.LifesMapper;
import com.room.service.LifeService;
import com.room.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
public class LifeController {
    @Autowired
    private LifeMapper lifeMapper;
    @Autowired
    private LifesMapper lifesMapper;
    @Autowired
    private LifeService lifeService;

    @Value("${mydata.upload-pathb}")
    private String localPath;


    //个人空间获得当前页的动态
    @ResponseBody
    @RequestMapping("/getlifes")
    public String getLifes(@RequestParam("username") String username,
                           @RequestParam("page") int page) {

        int pages = page * 10;
        List<Lifes> allLifes = lifesMapper.getAllLifes(pages, username);

        if (allLifes != null) {
            String json = JSON.toJSONString(allLifes);
            return json;
        }
        return "false";
    }

    //获得动态详情(一个动态)
    @ResponseBody
    @GetMapping("/life")
    public String oneLife(@RequestParam("l_id") int l_id) {

        Lifes oneLifes = lifesMapper.getOneLifes(l_id);
        if (oneLifes != null) {
            int look_c = oneLifes.getLook_c();
            look_c++;
            oneLifes.setLook_c(look_c);
            String json = JSON.toJSONString(oneLifes);
            lifeService.onlyAddLook(l_id);
//            System.out.println("返回动态详情及增加浏览数成功");
//            System.out.println(json);
            return json;
        }
        return "false";
    }

    //返回动态详情页面
    @RequestMapping("/lifeHtml")
    public String lifeHtml(@RequestParam("l_id") int l_id,
                           Model model) {
        model.addAttribute("l_id", l_id);
        return "life";
    }

    //获得我的主页的所有动态
    @ResponseBody
    @RequestMapping("/myRoomLife")
    public String myRoomLife(@RequestParam("author") String author,
                             @RequestParam("page") int page) {
        int pages = page * 10;
        List<Lifes> myAllLife = lifesMapper.getMyRoomLife(pages, author);
        if (myAllLife != null) {
            String json = JSON.toJSONString(myAllLife);
            return json;
        }
        return "false";
    }

    //获得公共圈所有动态
    @ResponseBody
    @RequestMapping("/publicRoomLife")
    public String publicRoomLife(@RequestParam("page") int page) {
        int pages = page * 10;
        List<Lifes> publicAllLife = lifesMapper.getPublicRoomLife(pages);
        if (publicAllLife != null) {
            String json = JSON.toJSONString(publicAllLife);
//            System.out.println(json);
            return json;
        }
        return "false";
    }

    //获得我刚创建的动态
    @ResponseBody
    @RequestMapping("/getMyNewLife")
    public String getMyNewLife(@RequestParam("author") String author) {
        List<Lifes> myNewLife = lifesMapper.myNewLifes(author);
        if (myNewLife != null) {
            String json = JSON.toJSONString(myNewLife);
//            System.out.println(json);
            return json;
        }
        return "false";
    }

    //创建动态
    @ResponseBody
    @RequestMapping("/pubLife")
    public String pubLife(@RequestParam("author") String author,
                          @RequestParam("l_att") String l_att,
                          @RequestParam("l_text") String l_text,
                          @RequestParam(value = "file",required = false) MultipartFile file) {
        Life life = new Life();
        life.setAuthor(author);
        life.setL_att(l_att);
        life.setL_text(l_text);

        if (file != null){
            String fileName = file.getOriginalFilename();
            String sqlPath = FileUtils.upload(file, localPath, fileName, 2);
            if (!sqlPath.equals("false")) {
//            System.out.println("动态图片上传成功，路径是：" + sqlPath);
                life.setUrl_1(sqlPath);
            }
        }

        lifeMapper.insertLife(life);
        return "true";
    }


    //删除动态
    @ResponseBody
    @RequestMapping("/delLife")
    public String delLife(@RequestParam("l_id") int l_id) {
        lifeMapper.delLife(l_id);
        return "true";
    }

    //更改动态公开属性
    @ResponseBody
    @RequestMapping("/altAtt")
    public String altAtt(@RequestParam("l_id") int l_id,
                         @RequestParam("l_att") int l_att) {
        if (l_att == 1)
            l_att = 2;
        else l_att = 1;
        lifeMapper.altL_att(l_id, l_att);
        return "true";
    }
}
