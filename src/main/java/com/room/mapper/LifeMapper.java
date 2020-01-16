package com.room.mapper;


import com.room.entity.Life;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LifeMapper {

    @Select("SELECT * FROM room.life where author in (SELECT f_friend_id FROM room.friend where f_user_id = #{username}) order by l_id desc limit 8")
    List<Life> getAllLife(String username);

    @Insert("insert into life (author,l_att,l_text,url_1) values (#{author},#{l_att},#{l_text},#{url_1})")
    void insertLife(Life life);

    @Select("select * from life where l_id = #{l_id}")
    Life getLife(int l_id);

    @Update("update life set like_c = #{like_c}, look_c = #{look_c}, com_c = #{com_c} where l_id = #{l_id}")
    void updLook(Life life);

    @Delete("delete from life where l_id = #{l_id}")
    void delLife(int l_id);

    @Update("update life set l_att = #{l_att} where l_id = #{l_id}")
    void altL_att(int l_id,int l_att);

}
