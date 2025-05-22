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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
        String difficulty = "all";

        when(stampRepository.findStamps(memberEmail, offset, size)).thenReturn(mockStamps);

        // when
        List<StampsResponseDto> result = stampService.findStamps(memberEmail, difficulty, page, size);

        // then
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(mockStamps.size());
        assertThat(result).isEqualTo(mockStamps);

        // verify
        verify(stampRepository, times(1)).findStamps(memberEmail, offset, size);
    }

    @Test
    @DisplayName("난이도별 스탬프 조회 성공 테스트")
    void findStamps_SpecificDifficulty_Success() {
        // given
        int page = 1;
        int size = 5;
        int offset = (page - 1) * size;
        String difficulty = "EASY";

        // easy 난이도만 필터링된 리스트 생성
        List<StampsResponseDto> easyStamps = mockStamps.stream()
                .filter(stamp -> stamp.getDifficulty().equals("EASY"))
                .toList();

        when(stampRepository.findStampsByDifficulty(memberEmail, difficulty, offset, size)).thenReturn(easyStamps);

        // when
        List<StampsResponseDto> result = stampService.findStamps(memberEmail, difficulty, page, size);

        // then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("title1", result.get(0).getTitle());
        assertEquals("EASY", result.get(0).getDifficulty());
    }

    @Test
    @DisplayName("난이도가 null일 때 모든 스탬프 조회 테스트")
    void findStamps_NullDifficulty_Success() {
        // given
        int page = 1;
        int size = 10;
        int offset = 0;
        String difficulty = null;

        when(stampRepository.findStamps(memberEmail, offset, size)).thenReturn(mockStamps);

        // when
        List<StampsResponseDto> result = stampService.findStamps(memberEmail, difficulty, page, size);

        // then
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    @DisplayName("회원이 보유한 스탬프가 없는 경우 예외 발생")
    void findStamps_whenNoStampsFound_throwsException() {
        // given
        String memberEmail = "no-stamps@ssafy.com";
        int page = 1;
        int size = 5;
        int offset = (page - 1) * size;
        String difficulty = "all";

        when(stampRepository.findStamps(memberEmail, offset, size))
                .thenReturn(Collections.emptyList());

        // when & then
        assertThatThrownBy(() -> stampService.findStamps(memberEmail, difficulty, page, size))
                .isInstanceOf(CustomException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.STAMP_LIST_EMPTY);

        verify(stampRepository).findStamps(memberEmail, offset, size);
    }

    @Test
    @DisplayName("로그인한 회원의 총 스탬프 수 반환 성공")
    void getTotalStamps_success() {
        String difficulty = "all";
        // given
        int expectedCount = 5;
        when(stampRepository.countStamps(memberEmail)).thenReturn(expectedCount);

        // when
        int result = stampService.getTotalStamps(memberEmail, difficulty);

        // then
        assertThat(result).isEqualTo(expectedCount);
        verify(stampRepository).countStamps(memberEmail);
    }

    @Test
    @DisplayName("난이도별 스탬프 개수 조회 테스트")
    void getTotalStamps_SpecificDifficulty_Success() {
        // given
        String difficulty = "hard";
        int expectedCount = 3;

        when(stampRepository.countStampsByDifficulty(memberEmail, difficulty)).thenReturn(expectedCount);

        // when
        int result = stampService.getTotalStamps(memberEmail, difficulty);

        // then
        assertEquals(expectedCount, result);
    }

    @Test
    @DisplayName("난이도가 null일 때 전체 스탬프 개수 조회 테스트")
    void getTotalStamps_NullDifficulty_Success() {
        // given
        String difficulty = null;
        int expectedCount = 5;

        when(stampRepository.countStamps(memberEmail)).thenReturn(expectedCount);

        // when
        int result = stampService.getTotalStamps(memberEmail, difficulty);

        // then
        assertEquals(expectedCount, result);
    }

    @Test
    @DisplayName("로그인한 회원의 총 스탬프가 없을 경우 0 반환 성공")
    void getTotalStamps_whenNoStamps_returnsZero() {
        String difficulty = "all";
        // given
        String memberEmail = "no-stamps@ssafy.com";
        when(stampRepository.countStamps(memberEmail)).thenReturn(0);

        // when
        int result = stampService.getTotalStamps(memberEmail, difficulty);

        // then
        assertThat(result).isZero();
        verify(stampRepository).countStamps(memberEmail);
    }
}
