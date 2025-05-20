package com.ssafy.questory.repository;

import com.ssafy.questory.domain.Title;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TitleMemberRepository {
    List<Title> findAllByMemberEmail(@Param("memberEmail") String memberEmail);
    int earn(@Param("memberEmail") String memberEmail, @Param("titleId") Long titleId);
}
