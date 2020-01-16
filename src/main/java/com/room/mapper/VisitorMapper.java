package com.room.mapper;

import com.room.dto.Visitors;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VisitorMapper {

    @Insert("insert into visitor (v_visitor, v_master) VALUES (#{v_visitor}, #{v_master})")
    void addVis(String v_visitor,String v_master);

    @Select("SELECT visitor.*,users.name,users.u_url FROM visitor,users where " +
            "visitor.v_visitor = users.username and visitor.v_master = #{v_master} order by v_id desc limit #{pages}, 8")
    List<Visitors> getRoomVis(String v_master,int pages);
}
