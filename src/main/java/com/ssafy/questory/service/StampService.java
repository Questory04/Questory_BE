package com.ssafy.questory.service;

import com.ssafy.questory.dto.response.stamp.StampsResponseDto;
import com.ssafy.questory.repository.StampRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StampService {

    private final StampRepository stampRepository;

    public List<StampsResponseDto> findStamps(int page, int size) {
        String memberEmail = "1@ssafy.com";   // 현재 로그인 되어 있는 사용자 정보 가져오기
        int offset  = (page-1) * size;
        return stampRepository.findStamps(memberEmail, offset, size);
    }

    public int getTotalStamps(){
        String  memberEmail = "1@ssafy.com";   // 현재 로그인 되어 있는 사용자 정보 가져오기
        return stampRepository.countStamps(memberEmail);
    }
}
