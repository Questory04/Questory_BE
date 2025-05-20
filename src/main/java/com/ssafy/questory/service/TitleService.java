package com.ssafy.questory.service;

import com.ssafy.questory.domain.Member;
import com.ssafy.questory.dto.request.title.TitleEarnRequestDto;
import com.ssafy.questory.repository.TitleMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TitleService {
    private final TitleMemberRepository titleMemberRepository;

    public void earn(Member member, TitleEarnRequestDto titleEarnRequestDto) {
        titleMemberRepository.earn(member.getEmail(), titleEarnRequestDto.getTitleId());
    }
}
