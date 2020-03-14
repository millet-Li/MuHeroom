package com.room.mapper;

import com.room.entity.Comment;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentMapper {

    @Select("SELECT * FROM room.comment where to_life_id in (select a.l_id from (SELECT l_id FROM room.life where author in (SELECT f_friend_id FROM room.friend where f_user_id = #{username}) order by l_id desc limit 8) as a)")
    List<Comment> getAllComment(String username);

    @Insert("insert into comment (user_id,to_user_id,c_type,to_life_id,c_text) values (#{user_id},#{to_user_id},#{c_type},#{to_life_id},#{c_text})")
    void subComment(Comment comment);

    @Insert("insert into comment (user_id,to_user_id,c_type,to_life_id,to_col_id,c_text) values (#{user_id},#{to_user_id},#{c_type},#{to_life_id},#{to_col_id},#{c_text})")
    void replyComment(Comment comment);

    @Delete("delete from comment where c_id = #{c_id}")
    void delComment(int c_id);

    @Delete("delete from comment where c_id = " +
            "(select a.c_id from " +
            "(select c_id from comment where user_id = #{user_id} " +
            "and to_user_id = #{to_user_id} and c_type = 5 and to_life_id = #{to_life_id})as a)")
    void delGly(String user_id,String to_user_id,int to_life_id);

    @Update("UPDATE room.comment SET or_read = 1 WHERE c_id = #{c_id}")
    void setNoticeRead(int c_id);

    @Update("UPDATE room.comment SET or_read = 0 WHERE c_id = #{c_id}")
    void setNoticeNotRead(int c_id);

    @Select("SELECT count(*) FROM room.comment where to_user_id = #{to_user_id} " +
            "and or_read = 0 and comment.user_id != #{to_user_id} ")
    int getNoticeNotRead(String to_user_id);

    @Insert("insert into comment (user_id,to_user_id,c_type,to_life_id) values (#{user_id},#{to_user_id},#{c_type},#{to_life_id})")
    void pointLike(Comment comment);

    @Select("SELECT * FROM room.comment " +
            "where user_id = #{user_id} and c_type = 5 and to_life_id in " +
            "(select a.l_id from " +
            "(SELECT l_id FROM room.life where author in " +
            "(SELECT f_friend_id FROM room.friend where f_user_id = #{user_id}) order by l_id desc limit #{pages}, 10) as a)")
    List<Comment> getLike(int pages,String user_id);

    @Select("SELECT * FROM room.comment " +
            " where user_id = #{user_id} and c_type = 5 and to_life_id in " +
            " (select a.l_id from " +
            " (SELECT l_id FROM room.life where author = #{author} order by l_id desc limit #{pages}, 10) as a)")
    List<Comment> getMyRomeLike(int pages,String user_id,String author);

    @Select("SELECT * FROM room.comment " +
            " where user_id = #{user_id} and c_type = 5 and to_life_id in " +
            " (select a.l_id from " +
            " (SELECT l_id FROM room.life where l_att = 2 order by l_id desc limit #{pages},10) as a)")
    List<Comment> getpublicRoomLike(int pages,String user_id);

    @Select("SELECT * FROM room.comment " +
            " where user_id = #{user_id} and c_type = 5 and to_life_id = #{to_life_id}")
    List<Comment> getLifeLike(String user_id,int to_life_id);
}
