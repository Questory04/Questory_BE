package com.ssafy.questory.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TitleMemberRepository {
    int earn(@Param("memberEmail") String memberEmail, @Param("titleId") Long titleId);
}
