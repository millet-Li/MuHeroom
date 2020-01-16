package com.room.service;

import com.room.entity.PerData;
import com.room.mapper.PerDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PerDataService {
    @Autowired
    PerDataMapper perDataMapper;

    public PerData select(String user_id){
        return perDataMapper.select(user_id);
    }

    public void insert(PerData perData){
        perDataMapper.insert(perData);
    }

    public void update(PerData perData){
        perDataMapper.update(perData);
    }

}
