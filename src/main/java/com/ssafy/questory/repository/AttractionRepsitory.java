package com.ssafy.questory.repository;

import com.ssafy.questory.dto.response.attraction.AttractionResponseDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AttractionRepsitory {
    List<AttractionResponseDto> searchAttractionByTitle(@Param("searchKeyword") String searchKeyword);
}
