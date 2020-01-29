package com.room.mapper;


import com.room.dto.Users;
import com.room.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {

    @Select("SELECT * FROM users where username = #{username}")
    User select(String username);

    @Insert("insert into users (username,password,name,email) values (#{username},#{password},#{name},#{email})")
    void insert(User user);

    @Update("update users set password = #{password} where username = #{username}")
    void update(User user);

    @Update("update users set name = #{name}, email = #{email}  where username = #{username}")
    void upd(User user);

    @Update("update users set password = #{password} where username = #{username}")
    void updPwd(User user);

    @Update("update users set u_url = #{sqlPath} where username = #{username}")
    void updateImages(String username,String sqlPath);

    @Select("select username,name,u_url,create_time from users where username = #{username}")
    Users selectImage(String username);

    @Select("SELECT username,name,u_url,create_time FROM users where name like '%${seaText}%' or username like '%${seaText}%' order by id limit #{pages},20")
    List<Users> seaUsers(int pages, String seaText);

}
