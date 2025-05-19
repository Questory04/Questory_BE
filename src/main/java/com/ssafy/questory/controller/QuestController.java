package com.ssafy.questory.controller;

import com.ssafy.questory.config.jwt.JwtService;
import com.ssafy.questory.dto.request.quest.QuestRequestDto;
import com.ssafy.questory.service.QuestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/quests")
@RequiredArgsConstructor
public class QuestController {
    private final QuestService questService;
    private final JwtService jwtService;

    @PostMapping("")
    public ResponseEntity<Map<String, String>> saveQuest(@RequestBody QuestRequestDto questRequestDto,
                                                         @RequestHeader("Authorization") String authorizationHeader){
        String token = authorizationHeader.substring(7);
        String memberEmail = jwtService.extractUsername(token);

        questService.saveQuest(questRequestDto, memberEmail);

        return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                "message", "성공적으로 퀘스트를 생성했습니다."));
    }
}
