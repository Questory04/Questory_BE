package com.ssafy.questory.controller;

import com.ssafy.questory.config.jwt.JwtService;
import com.ssafy.questory.dto.request.quest.QuestRequestDto;
import com.ssafy.questory.service.QuestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/quests")
@RequiredArgsConstructor
@Tag(name = "퀘스트 API", description = "퀘스트 관련 API")
public class QuestController {
    private final QuestService questService;
    private final JwtService jwtService;

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
    @Operation(summary = "퀘스트 수정", description = "퀘스트를 수정합니다.")
    public ResponseEntity<Map<String, String>> deleteQuest(@PathVariable int questId,
                                                           @RequestHeader("Authorization") String authorizationHeader){
        String token = authorizationHeader.substring(7);
        String memberEmail = jwtService.extractUsername(token);

        questService.deleteQuest(questId, memberEmail);

        return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                "message", "성공적으로 퀘스트를 삭제했습니다."));
    }
}
