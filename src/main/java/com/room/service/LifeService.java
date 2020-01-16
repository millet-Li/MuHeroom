package com.room.service;

import com.room.entity.Life;
import com.room.mapper.LifeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LifeService {
    @Autowired
    private LifeMapper lifeMapper;

    //加评论加浏览数
    public void upLook(int to_life_id) {
        Life life = lifeMapper.getLife(to_life_id);
        int look_c = life.getLook_c();
        int com_c = life.getCom_c();
        look_c++;
        com_c++;
        life.setLook_c(look_c);
        life.setCom_c(com_c);
        lifeMapper.updLook(life);
//        System.out.println("更新阅读数成功");
    }

    //加赞加浏览数
    public void addLike(int l_id) {
        Life life = lifeMapper.getLife(l_id);
        int look_c = life.getLook_c();
        int like_c = life.getLike_c();
        look_c++;
        like_c++;
        life.setLook_c(look_c);
        life.setLike_c(like_c);
        lifeMapper.updLook(life);
//        System.out.println("更新点赞数成功");

    }

    //减赞加浏览数
    public void reduceLike(int l_id) {
        Life life = lifeMapper.getLife(l_id);
        int look_c = life.getLook_c();
        int like_c = life.getLike_c();
        look_c++;
        like_c--;
        life.setLook_c(look_c);
        life.setLike_c(like_c);
        lifeMapper.updLook(life);
//        System.out.println("减少点赞数成功");

    }

    //只加浏览数
    public void onlyAddLook(int l_id) {
        Life life = lifeMapper.getLife(l_id);
        int look_c = life.getLook_c();
        look_c++;
        life.setLook_c(look_c);
        lifeMapper.updLook(life);
//        System.out.println("加浏览数成功");
    }

    //减评论加浏览数
    public void delComAddLook(int l_id) {
        Life life = lifeMapper.getLife(l_id);
        int look_c = life.getLook_c();
        int com_c = life.getCom_c();
        look_c++;
        com_c--;
        life.setLook_c(look_c);
        life.setCom_c(com_c);
        lifeMapper.updLook(life);
//        System.out.println("加浏览数成功");
    }
}
