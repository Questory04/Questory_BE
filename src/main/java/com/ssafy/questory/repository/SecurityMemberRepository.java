package com.ssafy.questory.repository;

import com.ssafy.questory.domain.Member;
import com.ssafy.questory.domain.SecurityMember;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface SecurityMemberRepository {
    Optional<SecurityMember> findByEmail(String email);
}
