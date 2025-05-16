package com.ssafy.questory.repository;


import com.ssafy.questory.dto.response.stamp.StampsResponseDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StampRepository {
    StampsResponseDto findStamps(int memberId);
}
