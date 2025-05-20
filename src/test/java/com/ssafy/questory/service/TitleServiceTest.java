package com.ssafy.questory.service;

import com.ssafy.questory.domain.Member;
import com.ssafy.questory.domain.Title;
import com.ssafy.questory.dto.request.title.TitleEarnRequestDto;
import com.ssafy.questory.dto.response.title.TitleInfoResponseDto;
import com.ssafy.questory.repository.TitleMemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class TitleServiceTest {

    private TitleMemberRepository titleMemberRepository;
    private TitleService titleService;

    @BeforeEach
    void setUp() {
        titleMemberRepository = mock(TitleMemberRepository.class);
        titleService = new TitleService(titleMemberRepository);
    }

    @Test
    @DisplayName("칭호 목록 조회 - 정상 작동")
    void testGetTitleInfo() {
        // given
        Member member = Member.builder().email("test@user.com").build();
        List<Title> mockTitles = List.of(
                Title.builder()
                        .titleId(1L)
                        .name("여행의 시작")
                        .createdAt(LocalDateTime.now())
                        .build()
        );
        when(titleMemberRepository.findAllByMemberEmail(member.getEmail())).thenReturn(mockTitles);

        // when
        List<TitleInfoResponseDto> result = titleService.getTitleInfo(member);

        // then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitleId()).isEqualTo(1L);
        assertThat(result.get(0).getName()).isEqualTo("여행의 시작");
        verify(titleMemberRepository).findAllByMemberEmail("test@user.com");
    }

    @Test
    @DisplayName("칭호 획득 - 호출 확인")
    void testEarnTitle() {
        // given
        Member member = Member.builder().email("test@user.com").build();
        TitleEarnRequestDto dto = TitleEarnRequestDto.builder().titleId(2L).build();

        // when
        titleService.earn(member, dto);

        // then
        verify(titleMemberRepository).earn("test@user.com", 2L);
    }
}
