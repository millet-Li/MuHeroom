package com.room.mapper;


import com.room.dto.Lifes;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LifesMapper {

    @Select("SELECT life.*,users.name,users.u_url " +
            "FROM room.life,room.users " +
            "where life.author = users.username " +
            "and life.author in " +
            "(SELECT f_friend_id FROM room.friend " +
            "where f_user_id = #{username}) " +
            "order by l_id desc limit #{pages}, 10")
    List<Lifes> getAllLifes(int pages,String username);

    @Select("select life.*,users.name,users.u_url from life,users where life.author = users.username " +
            "and life.l_id = #{l_id}")
    Lifes getOneLifes(int l_id);

    @Select("select life.*,users.name,users.u_url from life,users where life.author = users.username " +
            "and life.author = #{author} order by l_id desc limit 1")
    List<Lifes> myNewLifes(String author);

    @Select("SELECT life.*,users.name,users.u_url FROM room.life,room.users " +
            "where life.author = users.username and life.author = #{author} order by l_id desc limit #{pages}, 10")
    List<Lifes> getMyRoomLife(int pages,String author);

    @Select("SELECT life.*,users.name,users.u_url FROM room.life,room.users " +
            "where life.author = users.username and life.l_att = 2 order by life.l_id desc limit #{pages},10")
    List<Lifes> getPublicRoomLife(int pages);
}
