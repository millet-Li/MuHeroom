package com.room.controller;

import com.alibaba.fastjson.JSON;
import com.room.dto.Comments;
import com.room.entity.Comment;
import com.room.entity.Life;
import com.room.mapper.CommentMapper;
import com.room.mapper.CommentsMapper;
import com.room.mapper.LifeMapper;
import com.room.service.LifeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CommentController {

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private CommentsMapper commentsMapper;
    @Autowired
    private LifeService lifeService;


    //个人空间获得所有评论
    @ResponseBody
    @RequestMapping("/getcomments")
    public String getComments(@RequestParam("username") String username,
                              @RequestParam("page") int page) {
        int pages = page * 10;
        List<Comments> allComments = commentsMapper.getAllComments(pages, username);

        if (allComments != null) {
            String json = JSON.toJSONString(allComments);
//            System.out.println(json);
            return json;
        }
        return "no comment";
    }

    //获得一个动态的所有评论
    @ResponseBody
    @RequestMapping("/lifeComment")
    public String oneLifeComment(@RequestParam("l_id") int l_id) {
        List<Comments> oneLifeComments = commentsMapper.getOneLifeComments(l_id);
        if (oneLifeComments != null) {
            String json = JSON.toJSONString(oneLifeComments);
//            System.out.println(json);
            return json;
        }
        return "no comment";
    }

    //我的主页获得所有评论
    @ResponseBody
    @RequestMapping("/myRoomComment")
    public String myRoomCommennt(@RequestParam("author") String author,
                                 @RequestParam("page") int page) {
        int pages = page * 10;
        List<Comments> myRoomCommennts = commentsMapper.getMyRoomLifeComments(pages, author);
        if (myRoomCommennts != null) {
            String json = JSON.toJSONString(myRoomCommennts);
//            System.out.println(json);
            return json;
        }
        return "no comment";
    }

    //公共圈获得所有评论
    @ResponseBody
    @RequestMapping("/publicRoomComment")
    public String publicRoomCommennt(@RequestParam("page") int page) {
        int pages = page * 10;
        List<Comments> publicRoomCommennts = commentsMapper.getPublicRoomLifeComments(pages);
        if (publicRoomCommennts != null) {
            String json = JSON.toJSONString(publicRoomCommennts);
//            System.out.println(json);
            return json;
        }
        return "no comment";
    }

    //获得我刚生成的最新的评论或回复
    @ResponseBody
    @RequestMapping("/getNewCom")
    public String getNewCom(@RequestParam("user_id") String user_id,
                            @RequestParam("to_user_id") String to_user_id,
                            @RequestParam("to_life_id") int to_life_id) {
        List<Comments> myNewCom = commentsMapper.getMyNewCom(user_id, to_user_id, to_life_id);
        if (myNewCom != null) {
            String json = JSON.toJSONString(myNewCom);
//            System.out.println(json);
            return json;
        }
        return "no comment";
    }

    //获得用户在个人空间页面的点赞情况
    @ResponseBody
    @RequestMapping("/getMyLike")
    public String getLikes(@RequestParam("user_id") String user_id,
                           @RequestParam("page") int page
    ) {
        int pages = page * 10;
        List<Comment> userLike = commentMapper.getLike(pages, user_id);
        if (userLike.size() > 0) {
            String json = JSON.toJSONString(userLike);
//            System.out.println(json);
            return json;
        }
        return "false";
    }

    //获得用户在我的主页的点赞情况
    @ResponseBody
    @RequestMapping("/getMyRomeLike")
    public String getMyRomeLikes(@RequestParam("user_id") String user_id,
                                 @RequestParam("to_user_id") String to_user_id,
                                 @RequestParam("page") int page
    ) {
        int pages = page * 10;
        List<Comment> userRomeLike = commentMapper.getMyRomeLike(pages, user_id, to_user_id);
        if (userRomeLike.size() > 0) {
            String json = JSON.toJSONString(userRomeLike);
//            System.out.println(json);
            return json;
        }
        return "false";
    }

    //获得用户在公共空间的点赞情况
    @ResponseBody
    @RequestMapping("/getPublicRoomLike")
    public String getPublicRoomLikes(@RequestParam("user_id") String user_id,
                                     @RequestParam("page") int page
    ) {
        int pages = page * 10;
        List<Comment> userPublicRoomLike = commentMapper.getpublicRoomLike(pages, user_id);
        if (userPublicRoomLike.size() > 0) {
            String json = JSON.toJSONString(userPublicRoomLike);
//            System.out.println(json);
            return json;
        }
        return "false";
    }

    //获得用户在动态详情页的点赞情况
    @ResponseBody
    @RequestMapping("/getLifeLike")
    public String getLifeLikes(@RequestParam("user_id") String user_id,
                               @RequestParam("to_life_id") int to_life_id) {
        List<Comment> userLifeLike = commentMapper.getLifeLike(user_id, to_life_id);
        if (userLifeLike.size() > 0) {
            String json = JSON.toJSONString(userLifeLike);
//            System.out.println(json);
            return json;
        }
        return "false";
    }

    //评论
    @ResponseBody
    @RequestMapping("/subComment")
    public String subComment(@RequestParam("user_id") String user_id,
                             @RequestParam("to_user_id") String to_user_id,
                             @RequestParam("c_type") int c_type,
                             @RequestParam("to_life_id") int to_life_id,
                             @RequestParam("c_text") String c_text) {
        Comment comment = new Comment();
        comment.setUser_id(user_id);
        comment.setTo_user_id(to_user_id);
        comment.setC_type(c_type);
        comment.setTo_life_id(to_life_id);
        comment.setC_text(c_text);
        commentMapper.subComment(comment);
//        System.out.println("subCom true");
        lifeService.upLook(to_life_id);
        return "true";
    }

    //评论回复
    @ResponseBody
    @RequestMapping("/replyComment")
    public String replyComment(@RequestParam("user_id") String user_id,
                               @RequestParam("to_user_id") String to_user_id,
                               @RequestParam("c_type") int c_type,
                               @RequestParam("to_life_id") int to_life_id,
                               @RequestParam("to_col_id") int to_col_id,
                               @RequestParam("c_text") String c_text) {
        Comment comment = new Comment();
        comment.setUser_id(user_id);
        comment.setTo_user_id(to_user_id);
        comment.setC_type(c_type);
        comment.setTo_life_id(to_life_id);
        comment.setTo_col_id(to_col_id);
        comment.setC_text(c_text);
        commentMapper.replyComment(comment);

        lifeService.upLook(to_life_id);

        return "true";

    }

    //删除评论
    @ResponseBody
    @RequestMapping("/delComment")
    public String delComment(@RequestParam("c_id") int c_id,
                             @RequestParam("to_life_id") int to_life_id) {
        commentMapper.delComment(c_id);
        lifeService.delComAddLook(to_life_id);
        /*lifeService.delComAddLook();*/
//        System.out.println("del true");
        return "true";
    }


    //减赞
    @ResponseBody
    @RequestMapping("/redLike")
    public String redLike(@RequestParam("l_id") int l_id,
                          @RequestParam("user_id") String user_id,
                          @RequestParam("to_user_id") String to_user_id) {
        commentMapper.delGly(user_id, to_user_id, l_id);
        lifeService.reduceLike(l_id);
//        System.out.println("OK");
        return "true";
    }

    //加赞
    @ResponseBody
    @RequestMapping("/addLike")
    public String addLike(@RequestParam("l_id") int l_id,
                          @RequestParam("user_id") String user_id,
                          @RequestParam("to_user_id") String to_user_id) {
        Comment comment = new Comment();
        comment.setUser_id(user_id);
        comment.setTo_user_id(to_user_id);
        comment.setTo_life_id(l_id);
        comment.setC_type(5);
        commentMapper.pointLike(comment);
//        System.out.println("true");

        lifeService.addLike(l_id);
        return "true";
    }

    //提交留言
    @ResponseBody
    @RequestMapping("/subLeave")
    public String subLeave(@RequestParam("user_id") String user_id,
                           @RequestParam("to_user_id") String to_user_id,
                           @RequestParam("c_type") int c_type,
                           @RequestParam("c_text") String c_text) {
        Comment comment = new Comment();
        comment.setUser_id(user_id);
        comment.setTo_user_id(to_user_id);
        comment.setC_type(c_type);
        comment.setC_text(c_text);
        commentMapper.subComment(comment);
//        System.out.println("is true");
        return "true";
    }

    //获取我刚生成的留言及留言回复
    @ResponseBody
    @RequestMapping("/getNewLew")
    public String getNewLew(@RequestParam("user_id") String user_id,
                            @RequestParam("to_user_id") String to_user_id) {
        List<Comments> myNewLew = commentsMapper.getMyNewLew(user_id, to_user_id);
        if (myNewLew != null) {
            String json = JSON.toJSONString(myNewLew);
//            System.out.println(json);
            return json;
        }
        return "no Lew";
    }

    //获取留言
    @ResponseBody
    @RequestMapping("/myRoomLeave")
    public String myRoomLeave(@RequestParam("to_user_id") String to_user_id,
                              @RequestParam("page") int page) {
        int pages = page * 10;
        List<Comments> myRoomLeaves = commentsMapper.getLeave(pages, to_user_id);
        if (myRoomLeaves != null) {
            String json = JSON.toJSONString(myRoomLeaves);
            /*System.out.println(json);*/
            return json;
        }
        return "no comment";
    }

    //提交留言回复
    @ResponseBody
    @RequestMapping("/repLeave")
    public String repLeave(@RequestParam("user_id") String user_id,
                           @RequestParam("to_user_id") String to_user_id,
                           @RequestParam("c_type") int c_type,
                           @RequestParam("to_col_id") int to_col_id,
                           @RequestParam("c_text") String c_text) {
        Comment comment = new Comment();
        comment.setUser_id(user_id);
        comment.setTo_user_id(to_user_id);
        comment.setC_type(c_type);
        comment.setTo_col_id(to_col_id);
        comment.setC_text(c_text);
        commentMapper.replyComment(comment);
//        System.out.println("is true");
        return "true";
    }

    //获取留言回复
    @ResponseBody
    @RequestMapping("/myRoomToLeave")
    public String myRoomToLeave(@RequestParam("to_user_id") String to_user_id,
                                @RequestParam("page") int page) {
        int pages = page * 10;
        List<Comments> myRoomToLeaves = commentsMapper.getToLea(pages, to_user_id);
        if (myRoomToLeaves != null) {
            String json = JSON.toJSONString(myRoomToLeaves);
//            22
            return json;
        }
        return "no comment";
    }

    //删除留言
    @ResponseBody
    @RequestMapping("/delLea")
    public String delComment(@RequestParam("c_id") int c_id) {
        commentMapper.delComment(c_id);
        /*System.out.println("del true");*/
        return "true";
    }
}
