package com.ssafy.questory.repository;

import com.ssafy.questory.dto.request.quest.QuestRequestDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface QuestRepository {

    void saveQuest(@Param("questRequestDto") QuestRequestDto questRequestDto, @Param("memberEmail") String memberEmail, @Param("contentTypeId") int contentTypeId);

    int getAttractionById(@Param("attractionId")int attractionId);

    int getContentTypeIdByAttractionId(@Param("attractionId") int attractionId);

    void modifyQuest(int questId, QuestRequestDto questRequestDto);

    String getmemberEmailByQuestId(@Param("questId") int questId);

    void deleteQuest(@Param("questId") int questId);

    int getQuestCntByQuestId(@Param("questId") int questId);

    int getValidQuestCntByQuestId(@Param("questId") int questId);
}
