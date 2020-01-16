package com.room.service;

import com.room.entity.User;
import com.room.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;

    public User select(String username){
        return userMapper.select(username);
    }

    public void insert(User user){
        userMapper.insert(user);
    }

    public void update(User user){
        userMapper.update(user);
    }
}
