package com.ssafy.questory.service;

import com.ssafy.questory.dto.response.stamp.StampsResponseDto;
import com.ssafy.questory.repository.StampRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StampService {

    private final StampRepository stampRepository;

    public StampsResponseDto findStamps(int page, int size) {
        int memberId = 1;   // 현재 로그인 되어 있는 사용자 정보 가져오기
        StampsResponseDto stampsResponseDto = stampRepository.findStamps(memberId);
        return stampsResponseDto;
    }
}
