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

    @Test
    @DisplayName("퀘스트 수정")
    void modifyQuest_Success() {
        QuestRequestDto validQuestRequestDto = QuestRequestDto.builder()
                .attractionId(56644)
                .title("테스트 퀘스트")
                .questDescription("테스트 퀘스트 설명입니다.")
                .difficulty("MEDIUM")
                .isPrivate(false)
                .stampDescription("테스트 스탬프 설명")
                .build();
        int questId = 1;
        String memberEmail = "test@ssafy.com";

        // given
        when(questRepository.getAttractionById(validQuestRequestDto.getAttractionId())).thenReturn(1);
        when(questRepository.getmemberEmailByQuestId(questId)).thenReturn(memberEmail);

        // when
        questService.modifyQuest(questId, validQuestRequestDto, memberEmail);

        // then
        verify(questRepository, times(1)).modifyQuest(questId, validQuestRequestDto);
    }

    @Test
    @DisplayName("유효하지 않은 관광지 ID에 대한 퀘스트 생성")
    void modifyQuest_WithInvalidAttractionId_ThrowsException() {
        QuestRequestDto inValidQuestRequestDto = QuestRequestDto.builder()
                .attractionId(1)
                .title("테스트 퀘스트")
                .questDescription("테스트 퀘스트 설명입니다.")
                .difficulty("MEDIUM")
                .isPrivate(false)
                .stampDescription("테스트 스탬프 설명")
                .build();
        String memberEmail = "test@ssafy.com";
        int questId = 1;

        // given
        when(questRepository.getAttractionById(inValidQuestRequestDto.getAttractionId())).thenReturn(0);

        // when & then
        CustomException exception = assertThrows(CustomException.class, () -> {
            questService.modifyQuest(questId, inValidQuestRequestDto, memberEmail);
        });

        assertEquals(ErrorCode.ATTRACTION_NOT_FOUND, exception.getErrorCode());
        verify(questRepository, never()).modifyQuest(anyInt(), any(QuestRequestDto.class));
    }

    @Test
    @DisplayName("권한 없는 회원이 퀘스트 수정")
    void modifyQuest_WithUnauthorizedMember_ThrowsException() {
        QuestRequestDto validQuestRequestDto = QuestRequestDto.builder()
                .attractionId(56644)
                .title("테스트 퀘스트")
                .questDescription("테스트 퀘스트 설명입니다.")
                .difficulty("MEDIUM")
                .isPrivate(false)
                .stampDescription("테스트 스탬프 설명")
                .build();
        int questId = 1;
        String memberEmail = "test@ssafy.com";

        // given
        when(questRepository.getAttractionById(validQuestRequestDto.getAttractionId())).thenReturn(1);
        when(questRepository.getmemberEmailByQuestId(questId)).thenReturn("another@ssafy.com");

        // when & then
        CustomException exception = assertThrows(CustomException.class, () -> {
            questService.modifyQuest(questId, validQuestRequestDto, memberEmail);
        });

        assertEquals(ErrorCode.UNAUTHORIZED_MEMBER, exception.getErrorCode());
        verify(questRepository, never()).modifyQuest(anyInt(), any(QuestRequestDto.class));
    }
}