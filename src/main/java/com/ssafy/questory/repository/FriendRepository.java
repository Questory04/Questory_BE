package com.ssafy.questory.repository;

import com.ssafy.questory.domain.Friend;
import com.ssafy.questory.domain.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FriendRepository {
    List<Member> findFriendMembersByEmail(@Param("email") String email);
    List<Friend> findFollowRequestsByTargetEmail(@Param("targetEmail") String targetEmail);
    boolean existsByEmails(@Param("email1") String email, @Param("email2") String targetEmail);
    void deleteFriend(@Param("email1") String email1, @Param("email2") String email2);
    int request(Friend friend);
    int deleteFollowRequest(@Param("requesterEmail") String requesterEmail, @Param("targetEmail") String targetEmail);
    int insertFriendRelation(@Param("email1") String email1, @Param("email2") String email2);
    int update(Friend friend);
    int cancelRequest(@Param("requesterEmail") String requesterEmail, @Param("targetEmail") String targetEmail);
    List<Friend> findFollowRequestsByRequesterEmailWithPaging(@Param("requesterEmail") String email,
                                                              @Param("offset") int offset,
                                                              @Param("limit") int limit);
    int countFollowRequestsByRequesterEmail(@Param("requesterEmail") String email);
}
