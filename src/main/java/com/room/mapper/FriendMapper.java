package com.room.mapper;

import com.room.dto.Friends;
import com.room.entity.Friend;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendMapper {

    @Insert("insert into friend (f_user_id,f_friend_id) values (#{f_user_id},#{f_friend_id})")
    void addFriend(Friend friend);

    @Delete("delete from friend where f_id = #{f_id}")
    void delFriend(Friend friend);

    @Select("SELECT friend.*,users.name,users.u_url from friend,users " +
            " where friend.f_friend_id = users.username and friend.f_friend_id != #{username} and friend.f_user_id = #{username}" +
            " order by f_id desc limit #{pages}, 20")
    List<Friends> getFriends(String username,int pages);

    @Select("SELECT friend.*,users.name,users.u_url from friend,users " +
            "where friend.f_friend_id = users.username and friend.f_user_id = #{username} order by f_id desc limit 1")
    List<Friends> getNewFri(String username);

    @Select("SELECT * from friend where f_user_id = #{username} and f_friend_id != #{username}")
    List<Friend> getMyFriends(String username);

    @Select("SELECT friend.*,users.name,users.u_url from friend,users " +
            " where friend.f_user_id = users.username and friend.f_user_id != #{username} and friend.f_friend_id = #{username}" +
            " order by f_id desc limit #{pages}, 20")
    List<Friends> getFans(String username,int pages);

    @Select("SELECT * from friend where f_friend_id = #{username} and f_user_id != #{username}")
    List<Friend> getMyFans(String username);

    @Delete("DELETE FROM friend WHERE f_id = " +
            "(select a.f_id from ( select f_id from friend where f_user_id = #{f_user_id} and f_friend_id = #{f_friend_id} ) as a)")
    void deleteFriend(String f_user_id,String f_friend_id);
}
