package com.room.entity;


import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.sql.Timestamp;
@Data
public class Visitor {
    private int v_id;
    private String v_visitor;
    private String v_master;
    @JSONField(format = "MM-dd HH:mm")
    private Timestamp v_time;
}
