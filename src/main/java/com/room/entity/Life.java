package com.room.entity;


import com.alibaba.fastjson.annotation.JSONField;

import java.sql.Timestamp;

public class Life {
    private int l_id;
    private  String author;
    private  String l_att;
    private  String l_text;
    private  String url_1;
    private  String url_2;
    private String url_3;
    private  String url_4;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Timestamp l_crt;
    private int like_c;
    private int look_c;
    private int com_c;

    public int getL_id() {
        return l_id;
    }

    public void setL_id(int l_id) {
        this.l_id = l_id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getL_att() {
        return l_att;
    }

    public void setL_att(String l_att) {
        this.l_att = l_att;
    }

    public String getL_text() {
        return l_text;
    }

    public void setL_text(String l_text) {
        this.l_text = l_text;
    }

    public String getUrl_1() {
        return url_1;
    }

    public void setUrl_1(String url_1) {
        this.url_1 = url_1;
    }

    public String getUrl_2() {
        return url_2;
    }

    public void setUrl_2(String url_2) {
        this.url_2 = url_2;
    }

    public String getUrl_3() {
        return url_3;
    }

    public void setUrl_3(String url_3) {
        this.url_3 = url_3;
    }

    public String getUrl_4() {
        return url_4;
    }

    public void setUrl_4(String url_4) {
        this.url_4 = url_4;
    }

    public Timestamp getL_crt() {
        return l_crt;
    }

    public void setL_crt(Timestamp l_crt) {
        this.l_crt = l_crt;
    }

    public int getLike_c() {
        return like_c;
    }

    public void setLike_c(int like_c) {
        this.like_c = like_c;
    }

    public int getLook_c() {
        return look_c;
    }

    public void setLook_c(int look_c) {
        this.look_c = look_c;
    }

    public int getCom_c() {
        return com_c;
    }

    public void setCom_c(int com_c) {
        this.com_c = com_c;
    }

    @Override
    public String toString() {
        return "Life{" +
                "l_id=" + l_id +
                ", author='" + author + '\'' +
                ", l_att='" + l_att + '\'' +
                ", l_text='" + l_text + '\'' +
                ", url_1='" + url_1 + '\'' +
                ", url_2='" + url_2 + '\'' +
                ", url_3='" + url_3 + '\'' +
                ", url_4='" + url_4 + '\'' +
                ", l_crt=" + l_crt +
                ", like_c=" + like_c +
                ", look_c=" + look_c +
                ", com_c=" + com_c +
                '}';
    }
}
