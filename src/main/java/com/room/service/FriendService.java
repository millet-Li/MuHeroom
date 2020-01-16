package com.room.service;

import com.room.entity.Friend;
import com.room.mapper.FriendMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FriendService {

    @Autowired
    private FriendMapper friendMapper;

    public void addFriend(Friend friend){
        friendMapper.addFriend(friend);
    }

    public void delFriend(Friend friend){
        friendMapper.delFriend(friend);
    }

}
