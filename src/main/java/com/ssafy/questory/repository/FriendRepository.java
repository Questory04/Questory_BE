package com.ssafy.questory.repository;

import com.ssafy.questory.domain.Friend;
import com.ssafy.questory.domain.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface FriendRepository {
    Optional<Member> findFriendMembersByEmail(@Param("email") String email);
    List<Friend> findFollowRequestsByTargetEmail(@Param("targetEmail") String targetEmail);
    int request(Friend friend);
    int deleteFollowRequest(@Param("requesterEmail") String requesterEmail, @Param("targetEmail") String targetEmail);
    int insertFriendRelation(@Param("email1") String email1, @Param("email2") String email2);
    int update(Friend friend);
}
