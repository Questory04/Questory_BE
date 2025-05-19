package com.ssafy.questory.service;

import com.ssafy.questory.common.exception.CustomException;
import com.ssafy.questory.common.exception.ErrorCode;
import com.ssafy.questory.dto.request.quest.QuestRequestDto;
import com.ssafy.questory.repository.QuestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class QuestServiceTest {

    private QuestRepository questRepository = mock(QuestRepository.class);
    private QuestService questService;

    @BeforeEach
    void setUp() {
        questService = new QuestService(questRepository);
    }

    @Test
    @DisplayName("유효한 관광지 ID에 대한 퀘스트 생성")
    void saveQuest_success() {
        // given
        QuestRequestDto validQuestRequestDto = QuestRequestDto.builder()
                .attractionId(56644)
                .title("테스트 퀘스트")
                .questDescription("테스트 퀘스트 설명입니다.")
                .difficulty("MEDIUM")
                .isPrivate(false)
                .stampDescription("테스트 스탬프 설명")
                .build();
        String memberEmail = "test@ssafy.com";

        when(questRepository.getAttractionById(validQuestRequestDto.getAttractionId())).thenReturn(1);
        when(questRepository.getContentTypeIdByAttractionId(validQuestRequestDto.getAttractionId())).thenReturn(13);

        // when
        assertDoesNotThrow(() -> questService.saveQuest(validQuestRequestDto, memberEmail));

        // then
        verify(questRepository).saveQuest(validQuestRequestDto, memberEmail, 13);
    }

    @Test
    @DisplayName("유효하지 않은 관광지 ID에 대한 퀘스트 생성")
    void saveQuest_ThrowsException() {
        // given
        QuestRequestDto inValidQuestRequestDto = QuestRequestDto.builder()
                .attractionId(1)
                .title("테스트 퀘스트")
                .questDescription("테스트 퀘스트 설명입니다.")
                .difficulty("MEDIUM")
                .isPrivate(false)
                .stampDescription("테스트 스탬프 설명")
                .build();
        String memberEmail = "test@ssafy.com";

        when(questRepository.getAttractionById(inValidQuestRequestDto.getAttractionId())).thenReturn(0);

        // when & then
        CustomException thrown = assertThrows(CustomException.class, () ->
                questService.saveQuest(inValidQuestRequestDto, memberEmail));

        assertEquals(ErrorCode.ATTRACTION_NOT_FOUND, thrown.getErrorCode());

        verify(questRepository, never()).saveQuest(any(), any(), anyInt());
    }
}