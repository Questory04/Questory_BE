package com.ssafy.questory.service;

import com.ssafy.questory.common.exception.CustomException;
import com.ssafy.questory.common.exception.ErrorCode;
import com.ssafy.questory.dto.request.quest.QuestRequestDto;
import com.ssafy.questory.dto.response.quest.QuestsResponseDto;
import com.ssafy.questory.repository.QuestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestService {
    private final QuestRepository questRepository;

    public List<QuestsResponseDto> findQuests(int page, int size) {
        int offset  = (page-1) * size;
        List<QuestsResponseDto> questsResponseDtoList = questRepository.findQuests(offset, size);

        if(questsResponseDtoList.isEmpty()){
            throw new CustomException(ErrorCode.QUEST_LIST_EMPTY);
        }
        return questsResponseDtoList;
    }

    public int getTotalQuests() {
        return questRepository.getTotalQuests();
    }

    public List<QuestsResponseDto> findQuestsByMemberEmail(String memberEmail, int page, int size) {
        int offset  = (page-1) * size;
        List<QuestsResponseDto> questsResponseDtoList = questRepository.findQuestsByMemberEmail(memberEmail, offset, size);

        if(questsResponseDtoList.isEmpty()){
            throw new CustomException(ErrorCode.QUEST_LIST_EMPTY);
        }
        return questsResponseDtoList;
    }

    public int getTotalQuestsByMemberEmail(String memberEmail) {
        return questRepository.getTotalQuestsByMemberEmail(memberEmail);
    }

    public void saveQuest(QuestRequestDto questRequestDto, String memberEmail) {
        int isValidAttraction = questRepository.getAttractionById(questRequestDto.getAttractionId());
        if(isValidAttraction!=1){
            throw new CustomException(ErrorCode.ATTRACTION_NOT_FOUND);
        }

        int contentTypeId = questRepository.getContentTypeIdByAttractionId(questRequestDto.getAttractionId());
        questRepository.saveQuest(questRequestDto, memberEmail, contentTypeId);
    }

    public void modifyQuest(int questId, QuestRequestDto questRequestDto, String memberEmail) {
        int isValidAttraction = questRepository.getAttractionById(questRequestDto.getAttractionId());
        if(isValidAttraction!=1){
            throw new CustomException(ErrorCode.ATTRACTION_NOT_FOUND);
        }

        String memberEmailByQuestId = questRepository.getmemberEmailByQuestId(questId);
        if(!memberEmailByQuestId.equals(memberEmail)){
            throw new CustomException(ErrorCode.UNAUTHORIZED_MEMBER);
        }

        questRepository.modifyQuest(questId, questRequestDto);
    }

    public void deleteQuest(int questId, String memberEmail) {
        int questCntByQuestId = questRepository.getQuestCntByQuestId(questId);
        if(questCntByQuestId!=1){
            throw new CustomException(ErrorCode.QUEST_NOT_FOUND);
        }

        String memberEmailByQuestId = questRepository.getmemberEmailByQuestId(questId);
        if(!memberEmailByQuestId.equals(memberEmail)) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_MEMBER);
        }

        int validQuestCntByQuestId = questRepository.getValidQuestCntByQuestId(questId);
        if(validQuestCntByQuestId!=1){
            throw new CustomException(ErrorCode.QUEST_ALREADY_DELETED);
        }

        questRepository.deleteQuest(questId);
    }
}
