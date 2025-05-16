package com.ssafy.questory.repository;


import com.ssafy.questory.dto.response.stamp.StampsResponseDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StampRepository {
    List<StampsResponseDto> findStamps(int memberId);
}
