package com.ssafy.questory.repository;


import com.ssafy.questory.dto.response.stamp.StampsResponseDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;

@Mapper
public interface StampRepository {
    List<StampsResponseDto> findStamps(@Param("memberEmail") String memberEmail, @Param("offset") int offset, @Param("limit") int limit);
    int countStamps(@Param("memberEmail") String memberEmail);
}
