package com.room.entity;


import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.sql.Date;

@Data
public class PerData {
    private int id;
    private String user_id;
    @JSONField(format = "yyyy-MM-dd")
    private Date date;
    private String sex;
    private String locus;
    private String profe;
    private String room_name;
    private String mood;
    private String brief;
    private int vis_c;

}
