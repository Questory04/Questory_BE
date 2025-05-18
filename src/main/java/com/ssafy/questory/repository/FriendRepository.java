package com.ssafy.questory.repository;

import com.ssafy.questory.domain.Friend;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FriendRepository {
    int request(Friend friend);
}
