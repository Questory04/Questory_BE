package com.ssafy.questory.service;

import com.ssafy.questory.domain.Member;
import com.ssafy.questory.dto.request.title.TitleEarnRequestDto;
import com.ssafy.questory.dto.response.title.TitleInfoResponseDto;
import com.ssafy.questory.repository.TitleMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TitleService {
    private final TitleMemberRepository titleMemberRepository;

    public List<TitleInfoResponseDto> getTitleInfo(Member member) {
        return titleMemberRepository.findAllByMemberEmail(member.getEmail()).stream()
                .map(TitleInfoResponseDto::from)
                .toList();
    }

    public void earn(Member member, TitleEarnRequestDto titleEarnRequestDto) {
        titleMemberRepository.earn(member.getEmail(), titleEarnRequestDto.getTitleId());
    }
}
