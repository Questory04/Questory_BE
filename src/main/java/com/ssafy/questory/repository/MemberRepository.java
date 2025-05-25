package com.ssafy.questory.repository;

import com.ssafy.questory.domain.Member;
import com.ssafy.questory.dto.request.member.MemberModifyRequestDto;
import com.ssafy.questory.dto.response.member.MemberSearchResponseDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface MemberRepository {
    Optional<Member> findByEmail(String email);
    int regist(Member member);
    int changePassword(@Param("member") Member member, @Param("changedPassword") String changedPassword);
    int modify(Member member);
    int withdraw(Member member);
    List<Member> searchByEmailWithPaging(@Param("email") String email, @Param("requesterEmail") String requesterEmail,
                                         @Param("offset") int offset, @Param("limit") int limit);
    int countByEmail(@Param("email") String email);

    int getMemberExp(@Param("memberEmail") String memberEmail);
    void addMemberExp(@Param("memberEmail") String memberEmail, @Param("experiencePoints") int experiencePoints);
}
