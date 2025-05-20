package com.ssafy.questory.service;

import com.ssafy.questory.common.exception.CustomException;
import com.ssafy.questory.common.exception.ErrorCode;
import com.ssafy.questory.dto.response.stamp.StampsResponseDto;
import com.ssafy.questory.repository.StampRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class StampServiceTest {
    private StampRepository stampRepository = mock(StampRepository.class);
    private StampService stampService;

    private String memberEmail = "test@ssafy.com";
    private List<StampsResponseDto> mockStamps;

    @BeforeEach
    void setUp(){
        stampService = new StampService(stampRepository);

        StampsResponseDto stamp1 = StampsResponseDto.builder()
                .title("title1")
                .date(LocalDate.of(2025, 5, 10))
                .contentTypeName("contentType1")
                .sidoName("sido1")
                .difficulty("EASY")
                .description("description1")
                .build();

        StampsResponseDto stamp2 = StampsResponseDto.builder()
                .title("title2")
                .date(LocalDate.of(2025, 6, 25))
                .contentTypeName("contentType2")
                .sidoName("sido2")
                .difficulty("HARD")
                .description("description2")
                .build();
        mockStamps = Arrays.asList(stamp1, stamp2);
    }

    @Test
    @DisplayName("스탬프 목록 조회 성공")
    void findStamps_success() {
        // given
        int page = 1;
        int size = 5;
        int offset = (page - 1) * size;

        when(stampRepository.findStamps(memberEmail, offset, size)).thenReturn(mockStamps);

        // when
        List<StampsResponseDto> result = stampService.findStamps(memberEmail, page, size);

        // then
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(mockStamps.size());
        assertThat(result).isEqualTo(mockStamps);

        // verify
        verify(stampRepository, times(1)).findStamps(memberEmail, offset, size);
    }

    @Test
    @DisplayName("회원이 보유한 스탬프가 없는 경우 예외 발생")
    void findStamps_whenNoStampsFound_throwsException() {
        // given
        String memberEmail = "no-stamps@ssafy.com";
        int page = 1;
        int size = 5;
        int offset = (page - 1) * size;

        when(stampRepository.findStamps(memberEmail, offset, size))
                .thenReturn(Collections.emptyList());

        // when & then
        assertThatThrownBy(() -> stampService.findStamps(memberEmail, page, size))
                .isInstanceOf(CustomException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.STAMP_LIST_EMPTY);

        verify(stampRepository).findStamps(memberEmail, offset, size);
    }

    @Test
    @DisplayName("로그인한 회원의 총 스탬프 수 반환 성공")
    void getTotalStamps_success() {
        // given
        int expectedCount = 5;
        when(stampRepository.countStamps(memberEmail)).thenReturn(expectedCount);

        // when
        int result = stampService.getTotalStamps(memberEmail);

        // then
        assertThat(result).isEqualTo(expectedCount);
        verify(stampRepository).countStamps(memberEmail);
    }

    @Test
    @DisplayName("로그인한 회원의 총 스탬프가 없을 경우 0 반환 성공")
    void getTotalStamps_whenNoStamps_returnsZero() {
        // given
        String memberEmail = "no-stamps@ssafy.com";
        when(stampRepository.countStamps(memberEmail)).thenReturn(0);

        // when
        int result = stampService.getTotalStamps(memberEmail);

        // then
        assertThat(result).isZero();
        verify(stampRepository).countStamps(memberEmail);
    }
}
