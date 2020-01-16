package com.room.controller;

import com.room.entity.PerData;
import com.room.entity.User;
import com.room.mapper.PerDataMapper;
import com.room.mapper.VisitorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class MyController {
    @Autowired
    private VisitorMapper visitorMapper;
    @Autowired
    private PerDataMapper perDataMapper;

    @RequestMapping("/")
    public String index(){
        return "index";
    }

    @RequestMapping("/go")
    public String go(){
        return "login";
    }

    @RequestMapping("/room")
    public String room(@RequestParam("author") String author,
                       Model model,
                       HttpServletRequest httpServletRequest){
        HttpSession httpSession = httpServletRequest.getSession();
        User user = (User) httpSession.getAttribute("sessionUser");
        String username = user.getUsername();
        if (!username.equals(author)){
            visitorMapper.addVis(username,author);
            PerData perData = perDataMapper.select(author);
            int vis_c = perData.getVis_c();
            vis_c++;
            perDataMapper.addVis(vis_c,author);
        }
//        System.out.println("OK!!");
        model.addAttribute("author",author);
        return "myRoom";
    }

    @RequestMapping("/publicRoom")
    public String pubRoom(){
        return "publicRoom";
    }

    @RequestMapping("/notice")
    public String notice(){
        return "notice";
    }

    @RequestMapping("/friend")
    public String friend(){
        return "friend";
    }

    @RequestMapping("/search")
    public String search(@RequestParam("seaText") String seaText,
                         Model model){
        /*String text = "\"" + seaText + "\"";
        System.out.println(text);*/
        model.addAttribute("seaText", seaText);
        return "searchUser";
    }

    @RequestMapping("/play")
    public String play(){
        return "play";
    }

    @RequestMapping("/game")
    public String game(){
        return "game";
    }
}
