package com.ssafy.questory.repository;

import com.ssafy.questory.dto.request.quest.QuestRequestDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface QuestRepository {

    void saveQuest(@Param("questRequestDto") QuestRequestDto questRequestDto, @Param("memberEmail") String memberEmail);

    int getAttractionById(@Param("attractionId")int attractionId);
}
