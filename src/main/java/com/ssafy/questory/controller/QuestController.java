package com.ssafy.questory.controller;

import com.ssafy.questory.common.exception.CustomException;
import com.ssafy.questory.common.exception.ErrorCode;
import com.ssafy.questory.config.jwt.JwtService;
import com.ssafy.questory.dto.request.quest.QuestPositionRequestDto;
import com.ssafy.questory.dto.request.quest.QuestRequestDto;
import com.ssafy.questory.dto.response.quest.QuestRecommendationResponseDto;
import com.ssafy.questory.dto.response.quest.QuestResponseDto;
import com.ssafy.questory.dto.response.quest.QuestsResponseDto;
import com.ssafy.questory.service.QuestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/quests")
@RequiredArgsConstructor
@Tag(name = "퀘스트 API", description = "퀘스트 관련 API")
public class QuestController {
    private final QuestService questService;
    private final JwtService jwtService;

    @GetMapping("")
    @Operation(summary = "퀘스트 목록 조회", description = "퀘스트 목록을 조회합니다.")
    public ResponseEntity<Map<String, Object>> findQuests(@RequestParam(required = false) String difficulty,
                                                          @RequestParam(defaultValue = "1") int page,
                                                          @RequestParam(defaultValue = "6") int size,
                                                          @RequestHeader(value = "Authorization", required = false) String authorizationHeader) {
        List<QuestsResponseDto> questsResponseDtoList;
        int totalItems;
        int totalPages;
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            String token = authorizationHeader.substring(7);
            String memberEmail = jwtService.extractUsername(token);

            questsResponseDtoList = questService.findValidQuestsByMemberEmail(memberEmail, difficulty, page, size);
            totalItems = questService.getValidQuestsCntByMemberEmail(memberEmail, difficulty);
            totalPages = (int) Math.ceil((double) totalItems / size);
        }else{
            questsResponseDtoList = questService.findQuests(page, size);
            totalItems = questService.getTotalQuests();
            totalPages = (int) Math.ceil((double) totalItems / size);
        }

        Map<String, Object> pagination = new HashMap<>();
        pagination.put("currentPage", page);
        pagination.put("totalItems", totalItems);
        pagination.put("totalPages", totalPages);
        pagination.put("pageSize", size);

        Map<String, Object> response = new HashMap<>();
        response.put("quests", questsResponseDtoList);
        response.put("pagination", pagination);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/me/created")
    @Operation(summary = "생성한 퀘스트 목록 조회", description = "생성한 퀘스트 목록을 조회합니다.")
    public ResponseEntity<Map<String, Object>> findQuestsCreatedByMe(@RequestParam(required = false) String difficulty,
                                                          @RequestParam(defaultValue = "1") int page,
                                                          @RequestParam(defaultValue = "6") int size,
                                                          @RequestHeader(value = "Authorization", required = false) String authorizationHeader) {
        if(authorizationHeader == null){
            throw new CustomException(ErrorCode.TOKEN_NOT_FOUND);
        } else if(!authorizationHeader.startsWith("Bearer ")){
            throw new CustomException(ErrorCode.INVALID_TOKEN_FORMAT);
        }

        String token = authorizationHeader.substring(7);
        String memberEmail = jwtService.extractUsername(token);

        List<QuestsResponseDto> questsResponseDtoList = questService.findQuestsCreatedByMe(memberEmail, difficulty, page, size);

        int totalItems = questService.getQuestsCntCreatedByMe(memberEmail, difficulty);
        int totalPages = (int) Math.ceil((double) totalItems / size);

        Map<String, Object> pagination = new HashMap<>();
        pagination.put("currentPage", page);
        pagination.put("totalItems", totalItems);
        pagination.put("totalPages", totalPages);
        pagination.put("pageSize", size);

        Map<String, Object> response = new HashMap<>();
        response.put("quests", questsResponseDtoList);
        response.put("pagination", pagination);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/me/active")
    @Operation(summary = "진행 중인 퀘스트 목록 조회", description = "진행 중인 퀘스트 목록을 조회합니다.")
    public ResponseEntity<Map<String, Object>> findActiveQuestsByMemberEmail(@RequestParam(required = false) String difficulty,
                                                          @RequestParam(defaultValue = "1") int page,
                                                          @RequestParam(defaultValue = "6") int size,
                                                          @RequestHeader("Authorization") String authorizationHeader) {
        if(authorizationHeader == null){
            throw new CustomException(ErrorCode.TOKEN_NOT_FOUND);
        } else if(!authorizationHeader.startsWith("Bearer ")){
            throw new CustomException(ErrorCode.INVALID_TOKEN_FORMAT);
        }

        String token = authorizationHeader.substring(7);
        String memberEmail = jwtService.extractUsername(token);

        List<QuestsResponseDto> questsResponseDtoList = questService.findActiveQuestsByMemberEmail(memberEmail, difficulty, page, size);

        for(QuestsResponseDto dto : questsResponseDtoList){
            System.out.println(dto.toString());
        }

        int totalItems = questService.getActiveQuestsByMemberEmail(memberEmail, difficulty);
        System.out.println("totalItems : "+totalItems);
        int totalPages = (int) Math.ceil((double) totalItems / size);

        Map<String, Object> pagination = new HashMap<>();
        pagination.put("currentPage", page);
        pagination.put("totalItems", totalItems);
        pagination.put("totalPages", totalPages);
        pagination.put("pageSize", size);

        Map<String, Object> response = new HashMap<>();
        response.put("quests", questsResponseDtoList);
        response.put("pagination", pagination);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{questId}")
    @Operation(summary = "퀘스트 상세 조회", description = "퀘스트 id로 퀘스트를 조회합니다.")
    public ResponseEntity<Map<String, Object>> findQuestById(@PathVariable int questId) {
        QuestResponseDto questResponseDto = questService.findQuestById(questId);

        Map<String, Object> response = new HashMap<>();
        response.put("quest", questResponseDto);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("")
    @Operation(summary = "퀘스트 등록", description = "로그인한 사용자가 새로운 퀘스트를 생성합니다.")
    public ResponseEntity<Map<String, String>> saveQuest(@RequestBody QuestRequestDto questRequestDto,
                                                         @RequestHeader("Authorization") String authorizationHeader){
        String token = authorizationHeader.substring(7);
        String memberEmail = jwtService.extractUsername(token);

        questService.saveQuest(questRequestDto, memberEmail);

        return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                "message", "성공적으로 퀘스트를 생성했습니다."));
    }


    @PatchMapping("/{questId}")
    @Operation(summary = "퀘스트 수정", description = "퀘스트를 수정합니다.")
    public ResponseEntity<Map<String, String>> modifyQuest(@PathVariable int questId,
                                                           @RequestBody QuestRequestDto questRequestDto,
                                                         @RequestHeader("Authorization") String authorizationHeader){
        String token = authorizationHeader.substring(7);
        String memberEmail = jwtService.extractUsername(token);

        questService.modifyQuest(questId, questRequestDto, memberEmail);

        return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                "message", "성공적으로 퀘스트를 수정했습니다."));
    }

    @PatchMapping("/delete/{questId}")
    @Operation(summary = "퀘스트 삭제", description = "퀘스트를 삭제합니다.")
    public ResponseEntity<Map<String, String>> deleteQuest(@PathVariable int questId,
                                                           @RequestHeader("Authorization") String authorizationHeader){
        String token = authorizationHeader.substring(7);
        String memberEmail = jwtService.extractUsername(token);

        questService.deleteQuest(questId, memberEmail);

        return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                "message", "성공적으로 퀘스트를 삭제했습니다."));
    }

    @PatchMapping("/{questId}/cancel")
    @Operation(summary = "퀘스트 진행 취소", description = "퀘스트 진행을 취소합니다.")
    public ResponseEntity<Map<String, String>> cancelQuest(@PathVariable int questId,
                                                           @RequestHeader("Authorization") String authorizationHeader){
        String token = authorizationHeader.substring(7);
        String memberEmail = jwtService.extractUsername(token);

        questService.cancelQuest(questId, memberEmail);

        return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                "message", "성공적으로 퀘스트를 취소했습니다."));
    }

    @PatchMapping("/{questId}/start")
    @Operation(summary = "퀘스트 진행 시작", description = "퀘스트 진행을 시작합니다.")
    public ResponseEntity<Map<String, String>> startQuest(@PathVariable int questId,
                                                           @RequestHeader("Authorization") String authorizationHeader){
        String token = authorizationHeader.substring(7);
        String memberEmail = jwtService.extractUsername(token);

        questService.startQuest(questId, memberEmail);

        return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                "message", "성공적으로 퀘스트를 시작했습니다."));
    }

    @PatchMapping("/{questId}/complete")
    @Operation(summary = "퀘스트 완료", description = "퀘스트를 완료하였습니다.")
    public ResponseEntity<Map<String, String>> completeQuest(@PathVariable int questId,
                                                          @RequestBody QuestPositionRequestDto questPositionRequestDto,
                                                          @RequestHeader("Authorization") String authorizationHeader){
        String token = authorizationHeader.substring(7);
        String memberEmail = jwtService.extractUsername(token);

        questService.completeQuest(questId, questPositionRequestDto, memberEmail);

        return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                "message", "성공적으로 퀘스트를 완료했습니다."));
    }

    @GetMapping("/recommendation")
    @Operation(summary = "추천 퀘스트 조회", description = "인기 기반 + 최신성 가중치로 정렬한 상위 퀘스트 목록을 조회합니다.")
    public ResponseEntity<List<QuestRecommendationResponseDto>> getRecommendation(
            @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(questService.getRecommendation(limit));
    }
}
