package com.ssafy.questory.service;

import com.ssafy.questory.common.exception.CustomException;
import com.ssafy.questory.common.exception.ErrorCode;
import com.ssafy.questory.dto.request.quest.QuestRequestDto;
import com.ssafy.questory.repository.QuestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestService {
    private final QuestRepository questRepository;

    public void saveQuest(QuestRequestDto questRequestDto, String memberEmail) {
        int isValidAttraction = questRepository.getAttractionById(questRequestDto.getAttractionId());
        if(isValidAttraction!=1){
            throw new CustomException(ErrorCode.ATTRACTION_NOT_FOUND);
        }

        int contentTypeId = questRepository.getContentTypeIdByAttractionId(questRequestDto.getAttractionId());
        questRepository.saveQuest(questRequestDto, memberEmail, contentTypeId);
    }
}
