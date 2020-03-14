package com.room.controller;

import com.alibaba.fastjson.JSON;
import com.room.dto.Comments;
import com.room.entity.Comment;
import com.room.mapper.CommentMapper;
import com.room.mapper.CommentsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class NoticeController {

    @Autowired
    private CommentsMapper commentsMapper;
    @Autowired
    private CommentMapper commentMapper;

    //获得全部消息
    @ResponseBody
    @RequestMapping("/getNotice")
    public String notice(@RequestParam("to_user_id") String to_user_id,
                         @RequestParam("page") int page) {
        int pages = page*14;
        List<Comments> notices = commentsMapper.getNotices(pages,to_user_id);
//        int num = notices.size();
//        System.out.println(num);
        if (notices != null) {
            String json = JSON.toJSONString(notices);
//            System.out.println(json);
            return json;
        }
        return "no comment";
    }

    //设置消息已读
    @ResponseBody
    @RequestMapping("/setNoticeRead")
    public String setRead(@RequestParam("c_id") int c_id) {
        commentMapper.setNoticeRead(c_id);
//        System.out.println("true");
        return "true";
    }

    //设置消息未读
    @ResponseBody
    @RequestMapping("/setNoticeNotRead")
    public String setNotRead(@RequestParam("c_id") int c_id) {
        commentMapper.setNoticeNotRead(c_id);
//        System.out.println("true");
        return "true";
    }

    //查询消息未读数
    @ResponseBody
    @RequestMapping("/inqNotice")
    public int inqNotice(@RequestParam("to_user_id") String to_user_id) {
        int noticeNotReadNum = commentMapper.getNoticeNotRead(to_user_id);
//        System.out.println(num);
        return noticeNotReadNum;

    }
}
