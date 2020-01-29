package com.room.controller;

import com.alibaba.fastjson.JSON;
import com.room.dto.Users;
import com.room.entity.Friend;
import com.room.entity.PerData;
import com.room.entity.User;
import com.room.mapper.UserMapper;
import com.room.service.FriendService;
import com.room.service.PerDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private FriendService friendService;
    @Autowired
    private PerDataService perDataService;

//    @ResponseBody
//    @PostMapping("/loginA")
//    public String login(@RequestParam("username") String username,
//                        @RequestParam("password") String password,
//                        HttpSession httpSession){
//        User user = userMapper.select(username);
//        if (user != null && user.getPassword().equals(password)) {
//            httpSession.setAttribute("sessionUser", user);
//            return "true";
//        } else {
//            return "false";
//        }
//    }

    /*---------------------------退出登录---------------------------*/
    @RequestMapping("/logoutOld")
    public String logout(HttpServletRequest httpServletRequest){
        HttpSession httpSession = httpServletRequest.getSession();
        if (httpSession != null){
            User user = (User) httpSession.getAttribute("sessionUser");
          /*  System.out.println("此用户退出登录："+user.toString());*/
            /*------两种注销登录方式-------*/
  /*          httpSession.removeAttribute("sessionUser");*/
            httpSession.invalidate();
            return "redirect:/go";
        }else{
            return "redirect:/";
        }

    }

    /*---------------------------注册---------------------------*/
    @ResponseBody
    @PostMapping("/register")
    public String register(@RequestParam("username") String username,
                           @RequestParam("password") String password,
                           @RequestParam("name") String name,
                           @RequestParam("email") String email){
        User user = userMapper.select(username);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        if (user == null){
            user = new User();
            user.setUsername(username);
            user.setPassword(bCryptPasswordEncoder.encode(password));
//            System.out.println(user.getPassword());
            user.setName(name);
            user.setEmail(email);
            userMapper.insert(user);

            PerData perData = new PerData();
            perData.setUser_id(username);
            perDataService.insert(perData);

            Friend friend = new Friend();
            friend.setF_user_id(username);
            friend.setF_friend_id(username);
            friendService.addFriend(friend);
            return "true";
        }else {
            return "false";
        }
    }

    /*---------------------------更改密码---------------------------*/
    @ResponseBody
    @PostMapping("/update")
    public String update(@RequestParam("username") String username,
                           @RequestParam("password") String password,
                           @RequestParam("email") String email){
        User user = userMapper.select(username);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        if (user != null && user.getEmail().equals(email)){
            user.setPassword(bCryptPasswordEncoder.encode(password));
            userMapper.update(user);
            return "true";
        }else {
            return "false";
        }
    }

    /*---------------------------更改帐号信息---------------------------*/
    @PostMapping("/upd")
    public String upd(@RequestParam("username") String username,
                      @RequestParam("name") String name,
//                         @RequestParam("password") String password,
                         @RequestParam("email") String email,
                      HttpServletRequest httpServletRequest){
        User user = new User();
//        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        user.setUsername(username);
        user.setName(name);
//        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setEmail(email);
        userMapper.upd(user);
        user = userMapper.select(username);
        user.setPassword("");
        HttpSession httpSession = httpServletRequest.getSession();
        httpSession.setAttribute("sessionUser",user);
        return "redirect:/room?author="+username;
    }
//    更改密码
    @RequestMapping("/updPassword")
    public String updPassword(@RequestParam("username") String username,
                              @RequestParam("password") String password){
        User user = new User();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        user.setUsername(username);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        userMapper.updPwd(user);
        return "redirect:/room?author="+username;
    }
//获取昵称、头像、账号、注册时间
    @ResponseBody
    @GetMapping("/selectImages")
    public String selectImages(@RequestParam("username") String username){
        Users users = userMapper.selectImage(username);
        if (users != null){
            String json = JSON.toJSONString(users);
//            System.out.println(json);
            return json;
        }
        return "false";
    }
//查找用户
    @ResponseBody
    @RequestMapping("/searchUser")
    public String searchUser(@RequestParam("seaText") String seaText,
                             @RequestParam("page") int page){

        int pages = page * 20;
        List<Users> seaUs = userMapper.seaUsers(pages,seaText);
        if (seaUs != null) {
            String json = JSON.toJSONString(seaUs);
                /*System.out.println("true");*/
            return json;
        }

        return "no User";
    }
}
