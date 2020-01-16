package com.room.controller;

import com.alibaba.fastjson.JSON;
import com.room.dto.Friends;
import com.room.dto.Users;
import com.room.entity.Comment;
import com.room.entity.Friend;
import com.room.mapper.CommentMapper;
import com.room.mapper.FriendMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class FriendController {

    @Autowired
    private FriendMapper friendMapper;
    @Autowired
    private CommentMapper commentMapper;

    //获得我的关注列表
    @ResponseBody
    @RequestMapping("/getMyFriend")
    public String getMyFriend(@RequestParam("username") String username,
                              @RequestParam("page") int page) {
        if (page == -1) {
            List<Friend> myFriend = friendMapper.getMyFriends(username);
            if (myFriend != null) {
                String json = JSON.toJSONString(myFriend);
                return json;
            }
        } else {
            int pages = page * 20;
            List<Friends> myFriends = friendMapper.getFriends(username, pages);
            if (myFriends != null) {
                String json = JSON.toJSONString(myFriends);
                /*System.out.println("true");*/
                return json;
            }
        }
        return "no friend";
    }

    //获得刚关注的朋友
    @ResponseBody
    @RequestMapping("/getMyNewFriend")
    public String getMyNewFriend(@RequestParam("username") String username) {
        List<Friends> myNFriends = friendMapper.getNewFri(username);
        if (myNFriends != null) {
            String json = JSON.toJSONString(myNFriends);
            /*System.out.println("true");*/
            return json;
        }
        return "No!";
    }

    //获得我的粉丝列表
    @ResponseBody
    @RequestMapping("/getMyFans")
    public String getMyFans(@RequestParam("username") String username,
                            @RequestParam("page") int page) {

        if (page == -1) {
            List<Friend> myAllFans = friendMapper.getMyFans(username);
            if (myAllFans != null) {
                String json = JSON.toJSONString(myAllFans);
                return json;
            }
        } else {
            int pages = page * 20;
            List<Friends> myFans = friendMapper.getFans(username, pages);
            if (myFans != null) {
                String json = JSON.toJSONString(myFans);
//                System.out.println("true");
                return json;
            }
        }
        return "no friend";
    }

    //解除关注
    @ResponseBody
    @RequestMapping("/removeFriend")
    public String removeFriend(@RequestParam("f_user_id") String f_user_id,
                               @RequestParam("f_friend_id") String f_friend_id) {

        friendMapper.deleteFriend(f_user_id, f_friend_id);

        Comment comment = new Comment();
        comment.setUser_id(f_user_id);
        comment.setTo_user_id(f_friend_id);
        comment.setC_type(4);
        commentMapper.pointLike(comment);
        /*System.out.println("OK!");*/
        return "true";
    }

    //添加关注
    @ResponseBody
    @RequestMapping("/addFriend")
    public String addFriend(@RequestParam("f_user_id") String f_user_id,
                            @RequestParam("f_friend_id") String f_friend_id) {
        Friend friend = new Friend();
        friend.setF_user_id(f_user_id);
        friend.setF_friend_id(f_friend_id);
        friendMapper.addFriend(friend);

        Comment comment = new Comment();
        comment.setUser_id(f_user_id);
        comment.setTo_user_id(f_friend_id);
        comment.setC_type(3);
        commentMapper.pointLike(comment);
//        System.out.println("OK");
        return "true";
    }


}
