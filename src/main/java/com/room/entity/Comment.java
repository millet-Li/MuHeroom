package com.room.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class Comment {

    private int c_id;
    private String user_id;
    private String to_user_id;
    private int c_type;
    private int to_life_id;
    private int to_col_id;
    private String c_text;
    @JSONField(format = "MM-dd HH:mm")
    private Timestamp c_time;
    private int or_read;

}
