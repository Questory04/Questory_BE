package com.ssafy.questory.service;

import com.ssafy.questory.common.exception.CustomException;
import com.ssafy.questory.common.exception.ErrorCode;
import com.ssafy.questory.dto.response.attraction.AttractionResponseDto;
import com.ssafy.questory.repository.AttractionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AttractionService {
    private final AttractionRepository attractionRepository;

    public List<AttractionResponseDto> searchAttractionByTitle(String searchKeyword) {
        if(searchKeyword.isBlank()){
            throw new CustomException(ErrorCode.KEYWORD_EMPTY);
        }
        return attractionRepository.searchAttractionByTitle(searchKeyword);
    }

    public AttractionResponseDto searchAttractionById(int questId) {
        return attractionRepository.searchAttractionById(questId);
    }
}
