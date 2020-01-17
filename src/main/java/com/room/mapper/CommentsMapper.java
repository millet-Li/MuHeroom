package com.room.mapper;


import com.room.dto.Comments;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentsMapper {

//获得个人空间的评论
    @Select("SELECT comment.*,a.name,a.u_url,b.name as to_name FROM room.comment,room.users a,room.users b where comment.user_id = a.username and comment.to_user_id = b.username " +
            "and to_life_id in " +
            "(select c.l_id from " +
            "(SELECT l_id FROM room.life where author in " +
            "(SELECT f_friend_id FROM room.friend where f_user_id = #{username}) order by l_id desc limit #{pages}, 10) as c)")
    List<Comments> getAllComments(int pages,String username);

//获得一个动态的评论
    @Select("SELECT comment.*,a.name,a.u_url,b.name as to_name FROM room.comment,room.users a,room.users b where comment.user_id = a.username and comment.to_user_id = b.username "+
            "and comment.to_life_id = #{l_id}")
    List<Comments> getOneLifeComments(int l_id);

    @Select("SELECT comment.*,a.name,a.u_url,b.name as to_name FROM room.comment,room.users a,room.users b where comment.user_id = a.username and comment.to_user_id = b.username " +
            "and to_life_id in " +
            "(select c.l_id from " +
            "(SELECT l_id FROM room.life where author = #{author} order by l_id desc limit #{pages}, 10) as c)")
    List<Comments> getMyRoomLifeComments(int pages,String author);

    @Select("SELECT comment.*,a.name,a.u_url,b.name as to_name FROM room.comment,room.users a,room.users b where comment.user_id = a.username and comment.to_user_id = b.username " +
            "and to_life_id in " +
            "(select c.l_id from " +
            "(SELECT l_id FROM room.life where l_att = 2 order by l_id desc limit #{pages},10) as c)")
    List<Comments> getPublicRoomLifeComments(int pages);

//获得刚发布的评论
    @Select("SELECT comment.*,a.name,a.u_url,b.name as to_name FROM room.comment,room.users a,room.users b where comment.user_id = a.username and comment.to_user_id = b.username " +
            "and comment.c_id = (select a.c_id from " +
            "(select c_id from comment where user_id = #{user_id} " +
            "and to_user_id = #{to_user_id} and to_life_id = #{to_life_id} order by c_id desc limit 1)as a)")
    List<Comments> getMyNewCom(String user_id,String to_user_id,int to_life_id);

    @Select("SELECT comment.*,users.name,users.u_url FROM room.comment,room.users where comment.user_id = users.username " +
            "and comment.c_id = (select a.c_id from " +
            "(select c_id from comment where user_id = #{user_id} " +
            "and to_user_id = #{to_user_id} order by c_id desc limit 1)as a)")
    List<Comments> getMyNewLew(String user_id,String to_user_id);

    @Select("SELECT comment.*,users.name,users.u_url FROM room.comment,room.users " +
            "where comment.user_id = users.username and comment.to_user_id = #{to_user_id}" +
            " and comment.user_id != #{to_user_id} order by c_id desc limit #{pages}, 14")
    List<Comments> getNotices(int pages,String to_user_id);

    @Select("SELECT comment.*,users.name,users.u_url FROM room.comment,room.users where " +
            "comment.user_id = users.username and comment.c_type = 7 and " +
            "comment.to_user_id = #{to_user_id} order by comment.c_id desc limit #{pages},10")
    List<Comments> getLeave(int pages,String to_user_id);

    @Select("SELECT comment.*,users.name,users.u_url FROM room.comment,room.users where " +
            "comment.user_id = users.username and comment.to_col_id in " +
            "(select a.c_id from " +
            "(select comment.c_id from comment where comment.c_type = 7 and comment.to_user_id = #{to_user_id} " +
            "order by comment.c_id desc limit #{pages},10) as a)")
    List<Comments> getToLea(int pages,String to_user_id);
}
