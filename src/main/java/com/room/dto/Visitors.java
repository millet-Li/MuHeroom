package com.room.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class Visitors {
    private int v_id;
    private String v_visitor;
    private String v_master;
    @JSONField(format = "yyyy-MM-dd HH:mm")
    private Timestamp v_time;
    private String name;
    private String u_url;
}
