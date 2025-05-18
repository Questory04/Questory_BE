package com.ssafy.questory.repository;

import com.ssafy.questory.domain.Friend;
import com.ssafy.questory.domain.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@Mapper
public interface FriendRepository {
    Optional<Member> findFriendMembersByEmail(@Param("email") String email);
    int request(Friend friend);
}
