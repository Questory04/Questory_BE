package com.ssafy.questory.repository;

import com.ssafy.questory.domain.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface MemberRepository {
    Optional<Member> findByEmail(String email);
    Member regist(Member member);
}
