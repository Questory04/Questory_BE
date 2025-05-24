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

    List<QuestsResponseDto> findValidQuestsByMemberEmail(@Param("memberEmail") String memberEmail, @Param("offset") int offset, @Param("limit") int limit);

    int getValidQuestsCntByMemberEmail(@Param("memberEmail") String memberEmail);

    List<QuestsResponseDto> findValidQuestsByMemberEmailAndDifficulty(@Param("memberEmail") String memberEmail, @Param("difficulty") String difficulty, @Param("offset") int offset, @Param("limit") int limit);

    int getValidQuestsCntByMemberEmailAndDifficulty(@Param("memberEmail") String memberEmail, @Param("difficulty") String difficulty);

    List<QuestsResponseDto> findQuestsCreatedByMe(@Param("memberEmail") String memberEmail, @Param("offset") int offset, @Param("limit") int limit);

    int getTotalQuestsCreatedByMe(@Param("memberEmail") String memberEmail);

    List<QuestsResponseDto> findQuestsCreatedByMeAndDifficulty(@Param("memberEmail") String memberEmail, @Param("difficulty") String difficulty, @Param("offset") int offset, @Param("limit") int limit);

    int getTotalQuestsCreatedByMeAndDifficulty(@Param("memberEmail") String memberEmail, @Param("difficulty") String difficulty);

    void saveQuest(@Param("questRequestDto") QuestRequestDto questRequestDto, @Param("memberEmail") String memberEmail, @Param("contentTypeId") int contentTypeId);

    int getAttractionById(@Param("attractionId")int attractionId);

    int getAttractionIdByQuestId(@Param("questId") int questId);

    int getContentTypeIdByAttractionId(@Param("attractionId") int attractionId);

    void modifyQuest(int questId, QuestRequestDto questRequestDto);

    String getmemberEmailByQuestId(@Param("questId") int questId);

    void deleteQuest(@Param("questId") int questId);

    int getQuestCntByQuestId(@Param("questId") int questId);

    int getValidQuestCntByQuestId(@Param("questId") int questId);

    QuestResponseDto findQuestById(@Param("questId") int questId);

    List<QuestsResponseDto> findActiveQuestsByMemberEmail(@Param("memberEmail") String memberEmail, @Param("offset") int offset, @Param("limit") int limit);

    List<QuestsResponseDto> findActiveQuestsByMemberEmailAndDifficulty(@Param("memberEmail") String memberEmail, @Param("difficulty") String difficulty, @Param("offset") int offset, @Param("limit") int limit);

    int getActiveQuestsByMemberEmail(@Param("memberEmail") String memberEmail);

    int getActiveQuestsByMemberEmailAndDifficulty(@Param("memberEmail") String memberEmail, @Param("difficulty") String difficulty);

    void cancelQuest(@Param("questId") int questId);

    void startQuest(@Param("questId") int questId, @Param("memberEmail") String memberEmail);

    int getMemberQuestCntByQuestId(@Param("questId") int questId, @Param("memberEmail") String memberEmail);

    int getInProgressQuestCntByQuestId(@Param("questId") int questId);
}
