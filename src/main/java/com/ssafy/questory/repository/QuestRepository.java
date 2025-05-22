package com.ssafy.questory.repository;

import com.ssafy.questory.dto.request.quest.QuestRequestDto;
import com.ssafy.questory.dto.response.attraction.AttractionResponseDto;
import com.ssafy.questory.dto.response.quest.QuestResponseDto;
import com.ssafy.questory.dto.response.quest.QuestsResponseDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface QuestRepository {

    List<QuestsResponseDto> findQuests(@Param("offset") int offset, @Param("limit") int limit);

    int getTotalQuests();

    List<QuestsResponseDto> findQuestsByMemberEmail(@Param("memberEmail") String memberEmail, @Param("offset") int offset, @Param("limit") int limit);

    int getTotalQuestsByMemberEmail(@Param("memberEmail") String memberEmail);

    List<QuestsResponseDto> findQuestsByMemberEmailAndDifficulty(@Param("memberEmail") String memberEmail, @Param("difficulty") String difficulty, @Param("offset") int offset, @Param("limit") int limit);

    int getTotalQuestsByMemberEmailAndDifficulty(@Param("memberEmail") String memberEmail, @Param("difficulty") String difficulty);

    void saveQuest(@Param("questRequestDto") QuestRequestDto questRequestDto, @Param("memberEmail") String memberEmail, @Param("contentTypeId") int contentTypeId);

    int getAttractionById(@Param("attractionId")int attractionId);

    int getContentTypeIdByAttractionId(@Param("attractionId") int attractionId);

    void modifyQuest(int questId, QuestRequestDto questRequestDto);

    String getmemberEmailByQuestId(@Param("questId") int questId);

    void deleteQuest(@Param("questId") int questId);

    int getQuestCntByQuestId(@Param("questId") int questId);

    int getValidQuestCntByQuestId(@Param("questId") int questId);

    QuestResponseDto findQuestById(@Param("questId") int questId);
}
