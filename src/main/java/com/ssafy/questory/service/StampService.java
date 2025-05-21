package com.ssafy.questory.service;

import com.ssafy.questory.common.exception.CustomException;
import com.ssafy.questory.common.exception.ErrorCode;
import com.ssafy.questory.dto.response.stamp.StampsResponseDto;
import com.ssafy.questory.repository.StampRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StampService {

    private final StampRepository stampRepository;

    public List<StampsResponseDto> findStamps(String memberEmail, String difficulty, int page, int size) {
        int offset  = (page-1) * size;

        List<StampsResponseDto> stampsResponseDtoList;
        if(difficulty== null || difficulty.equals("all")){
            stampsResponseDtoList = stampRepository.findStamps(memberEmail, offset, size);
        }else{
            stampsResponseDtoList = stampRepository.findStampsByDifficulty(memberEmail, difficulty, offset, size);
        }

        if(stampsResponseDtoList.isEmpty()){
            throw new CustomException(ErrorCode.STAMP_LIST_EMPTY);
        }
        return stampsResponseDtoList;
    }
    public int getTotalStamps(String memberEmail, String difficulty){
        if(difficulty== null || difficulty.equals("all")){
            return stampRepository.countStamps(memberEmail);
        }else{
            return stampRepository.countStampsByDifficulty(memberEmail, difficulty);
        }
    }
}
