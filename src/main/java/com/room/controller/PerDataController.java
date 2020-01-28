package com.room.controller;

import com.alibaba.fastjson.JSON;
import com.room.entity.PerData;
import com.room.service.PerDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Date;


@Controller
public class PerDataController {

    @Autowired
    private PerDataService perDataService;

    /*------------------获取个人资料---------------------*/
    @ResponseBody
    @GetMapping("/getperdata")
    public String getPerData(@RequestParam("user_id") String user_id){
        PerData perData = perDataService.select(user_id);
        if(perData != null){
/*            Object object = new Date();
            String json = JSON.toJSONStringWithDateFormat(perData,"yyyy-MM-dd");*/
            String json = JSON.toJSONString(perData);
/*            System.out.println(json);*/
            return json;
        }else {
            return "false";
        }
    }

    /*------------------更新个人资料---------------------*/
    @PostMapping("/upperData")
    public String updatePerData(@RequestParam("user_id") String user_id,
                                @RequestParam("date") Date date,
                                @RequestParam("sex") String sex,
                                @RequestParam("locus") String locus,
                                @RequestParam("profe") String profe,
                                @RequestParam("room_name") String room_name,
                                @RequestParam("mood") String mood,
                                @RequestParam("brief") String brief){
        PerData perData = perDataService.select(user_id);
            perData.setUser_id(user_id);
            perData.setDate(date);
            perData.setSex(sex);
            perData.setLocus(locus);
            perData.setProfe(profe);
            perData.setRoom_name(room_name);
            perData.setMood(mood);
            perData.setBrief(brief);
            perDataService.update(perData);
        /*    System.out.println("更新成功");*/
            return "redirect:/room?author="+user_id;

    }

}
