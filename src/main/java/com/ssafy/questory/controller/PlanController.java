package com.ssafy.questory.controller;

import com.ssafy.questory.domain.Member;
import com.ssafy.questory.dto.request.plan.PlanCreateRequestDto;
import com.ssafy.questory.service.PlanService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/plans")
@RequiredArgsConstructor
public class PlanController {
    private final PlanService planService;

    @PostMapping()
    @Operation(summary = "친구 목록 조회", description = "나의 친구 목록을 조회합니다.")
    public ResponseEntity<Map<String, String>> create(
            @AuthenticationPrincipal(expression = "member") Member member,
            @RequestBody PlanCreateRequestDto planCreateRequestDto) {
        planService.create(member, planCreateRequestDto);
        return ResponseEntity.ok().body(Map.of(
                "message", "여행 계획이 생성되었습니다."
        ));
    }
}
