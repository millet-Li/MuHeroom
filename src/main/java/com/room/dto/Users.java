package com.room.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class Users {
    private String username;
    private String name;
    private String u_url;
    @JSONField(format = "yyyy-MM-dd")
    private Timestamp create_time;
}
