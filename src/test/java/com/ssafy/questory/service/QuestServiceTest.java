package com.ssafy.questory.service;

import com.ssafy.questory.common.exception.CustomException;
import com.ssafy.questory.common.exception.ErrorCode;
import com.ssafy.questory.dto.request.quest.QuestRequestDto;
import com.ssafy.questory.dto.response.quest.QuestsResponseDto;
import com.ssafy.questory.repository.QuestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class QuestServiceTest {

    private QuestRepository questRepository = mock(QuestRepository.class);
    private QuestService questService;

    private List<QuestsResponseDto> mockQuests;

    @BeforeEach
    void setUp() {
        questService = new QuestService(questRepository);

        QuestsResponseDto quest1 = QuestsResponseDto.builder()
                .questTitle("title1")
                .attractionImage("url1")
                .attractionTitle("name1")
                .contentTypeName("contentType1")
                .attractionAddress("addr1")
                .questDifficulty("EASY")
                .questDescription("description1")
                .build();

        QuestsResponseDto quest2 = QuestsResponseDto.builder()
                .questTitle("title2")
                .attractionImage("url2")
                .attractionTitle("name2")
                .contentTypeName("contentType2")
                .attractionAddress("addr2")
                .questDifficulty("EASY")
                .questDescription("description2")
                .build();
        mockQuests = Arrays.asList(quest1, quest2);
    }

    @Test
    @DisplayName("전체 퀘스트 목록 조회 성공")
    void findQuests_ReturnsQuestList() {
        // given
        int page = 1;
        int size = 5;
        int offset = (page - 1) * size;

        when(questRepository.findQuests(offset, size)).thenReturn(mockQuests);

        // when
        List<QuestsResponseDto> result = questService.findQuests(page, size);

        // then
        assertNotNull(result);
        assertEquals(mockQuests.size(), result.size());
        verify(questRepository, times(1)).findQuests(offset, size);
    }

    @Test
    @DisplayName("퀘스트 목록이 비어있을 경우 예외 발생")
    void findQuests_ThrowsException_WhenQuestListEmpty() {
        // given
        int page = 1;
        int size = 10;
        int offset = (page - 1) * size;

        when(questRepository.findQuests(offset, size)).thenReturn(new ArrayList<>());

        // when & then
        CustomException exception = assertThrows(CustomException.class, () -> {
            questService.findQuests(page, size);
        });

        assertEquals(ErrorCode.QUEST_LIST_EMPTY, exception.getErrorCode());
        verify(questRepository, times(1)).findQuests(offset, size);
    }

    @Test
    @DisplayName("전체 퀘스트 수 반환")
    void getTotalQuests_ReturnsTotalCount() {
        // given
        int expectedTotal = 15;
        when(questRepository.getTotalQuests()).thenReturn(expectedTotal);

        // when
        int result = questService.getTotalQuests();

        // then
        assertEquals(expectedTotal, result);
        verify(questRepository, times(1)).getTotalQuests();
    }

    @Test
    @DisplayName("특정 회원이 실행 가능한 퀘스트 목록 조회")
    void findQuestsByMemberEmail_ReturnsQuestList() {
        // given
        String testEmail = "test@example.com";

        int page = 1;
        int size = 5;
        int offset = (page - 1) * size;

        when(questRepository.findQuestsByMemberEmail(eq(testEmail), eq(offset), eq(size))).thenReturn(mockQuests);

        // when
        List<QuestsResponseDto> result = questService.findQuestsByMemberEmail(testEmail, page, size);

        // then
        assertNotNull(result);
        assertEquals(mockQuests.size(), result.size());
        assertEquals("title1", result.get(0).getQuestTitle());
        assertEquals("url2", result.get(1).getAttractionImage());
        assertEquals("addr2", result.get(1).getAttractionAddress());
        verify(questRepository, times(1)).findQuestsByMemberEmail(testEmail, offset, size);
    }

    @Test
    @DisplayName("특정 회원의 퀘스트 목록이 비어있을 경우 예외 발생")
    void findQuestsByMemberEmail_ThrowsException_WhenQuestListEmpty() {
        // given
        String testEmail = "test@example.com";

        int page = 1;
        int size = 10;
        int offset = (page - 1) * size;

        when(questRepository.findQuestsByMemberEmail(eq(testEmail), eq(offset), eq(size))).thenReturn(new ArrayList<>());

        // when & then
        CustomException exception = assertThrows(CustomException.class, () -> {
            questService.findQuestsByMemberEmail(testEmail, page, size);
        });

        assertEquals(ErrorCode.QUEST_LIST_EMPTY, exception.getErrorCode());
        verify(questRepository, times(1)).findQuestsByMemberEmail(testEmail, offset, size);
    }

    @Test
    @DisplayName("특정 회원이 실행 가능한 퀘스트 수 반환")
    void getTotalQuestsByMemberEmail_ReturnsTotalCount() {
        // given
        String testEmail = "test@example.com";

        int expectedTotal = 8;
        when(questRepository.getTotalQuestsByMemberEmail(testEmail)).thenReturn(expectedTotal);

        // when
        int result = questService.getTotalQuestsByMemberEmail(testEmail);

        // then
        assertEquals(expectedTotal, result);
        verify(questRepository, times(1)).getTotalQuestsByMemberEmail(testEmail);
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

    @Test
    @DisplayName("퀘스트 삭제 성공")
    void deleteQuest_Success() {
        int questId = 1;
        String memberEmail = "test@ssafy.com";

        // given
        when(questRepository.getQuestCntByQuestId(questId)).thenReturn(1);
        when(questRepository.getmemberEmailByQuestId(questId)).thenReturn(memberEmail);
        when(questRepository.getValidQuestCntByQuestId(questId)).thenReturn(1);

        // when
        questService.deleteQuest(questId, memberEmail);

        // then
        verify(questRepository, times(1)).deleteQuest(questId);
    }

    @Test
    @DisplayName("존재하지 않는 퀘스트 삭제")
    void deleteQuest_QuestNotFound_ThrowsException() {
        int questId = 1;
        String memberEmail = "test@ssafy.com";

        // given
        when(questRepository.getQuestCntByQuestId(questId)).thenReturn(0);

        // when & then
        CustomException exception = assertThrows(CustomException.class, () -> {
            questService.deleteQuest(questId, memberEmail);
        });

        assertEquals(ErrorCode.QUEST_NOT_FOUND, exception.getErrorCode());
        verify(questRepository, never()).deleteQuest(anyInt());
    }

    @Test
    @DisplayName("권한 없는 회원이 퀘스트 삭제")
    void deleteQuest_UnauthorizedMember_ThrowsException() {
        int questId = 1;
        String memberEmail = "test@ssafy.com";

        // given
        when(questRepository.getQuestCntByQuestId(questId)).thenReturn(1);
        when(questRepository.getmemberEmailByQuestId(questId)).thenReturn("another@ssafy.com");

        // when & then
        CustomException exception = assertThrows(CustomException.class, () -> {
            questService.deleteQuest(questId, memberEmail);
        });

        assertEquals(ErrorCode.UNAUTHORIZED_MEMBER, exception.getErrorCode());
        verify(questRepository, never()).deleteQuest(anyInt());
    }

    @Test
    @DisplayName("이미 삭제된 퀘스트 삭제 시 예외 발생 테스트")
    void deleteQuest_AlreadyDeleted_ThrowsException() {
        int questId = 1;
        String memberEmail = "test@ssafy.com";

        // given
        when(questRepository.getQuestCntByQuestId(questId)).thenReturn(1);
        when(questRepository.getmemberEmailByQuestId(questId)).thenReturn(memberEmail);
        when(questRepository.getValidQuestCntByQuestId(questId)).thenReturn(0);

        // when & then
        CustomException exception = assertThrows(CustomException.class, () -> {
            questService.deleteQuest(questId, memberEmail);
        });

        assertEquals(ErrorCode.QUEST_ALREADY_DELETED, exception.getErrorCode());
        verify(questRepository, never()).deleteQuest(anyInt());
    }
}