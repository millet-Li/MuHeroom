package com.room.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TestController {

    @RequestMapping("/perData")
    public String personalData(@RequestParam("user_id") String user_id,
                               Model model){
        model.addAttribute("user_id",user_id);
        return "personalData";
    }

    @RequestMapping("/uperdata")
    public String upPerData(){

        return "upperdata";
    }
}
