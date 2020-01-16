package com.room.mapper;

import com.room.entity.PerData;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface PerDataMapper {

    @Select("SELECT * FROM room.per_data where user_id = #{user_id}")
    PerData select(String user_id);

    @Insert("INSERT INTO per_data (user_id,date,sex,locus,profe,room_name,mood,brief) VALUES (#{user_id},#{date},#{sex},#{locus},#{profe},#{room_name},#{mood},#{brief})")
    void insert(PerData perData);

    @Update("UPDATE per_data SET date = #{date}, sex = #{sex}, locus = #{locus}, profe = #{profe}, room_name = #{room_name}, mood = #{mood}, brief = #{brief} WHERE user_id = #{user_id}")
    void update(PerData perData);

    @Update("update per_data SET vis_c = #{vis_c} where user_id = #{user_id}")
    void addVis(int vis_c,String user_id);
}
