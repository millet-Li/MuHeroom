package com.room.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class Lifes {

    private int l_id;
    private  String author;
    private  String l_att;
    private  String l_text;
    private  String url_1;
    private  String url_2;
    private String url_3;
    private  String url_4;
    @JSONField(format = "yyyy-MM-dd HH:mm")
    private Timestamp l_crt;
    private int like_c;
    private int look_c;
    private int com_c;
    private String name;
    private String u_url;

}
