package com.ssafy.questory.repository;

import com.ssafy.questory.domain.Member;
import com.ssafy.questory.dto.request.member.MemberModifyRequestDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@Mapper
public interface MemberRepository {
    Optional<Member> findByEmail(String email);
    int regist(Member member);
    int changePassword(@Param("member") Member member, @Param("changedPassword") String changedPassword);
    int modify(Member member);
    int withdraw(Member member);
}
