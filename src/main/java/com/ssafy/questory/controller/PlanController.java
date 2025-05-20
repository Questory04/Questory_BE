package com.ssafy.questory.controller;

import com.ssafy.questory.domain.Member;
import com.ssafy.questory.dto.request.plan.PlanCreateRequestDto;
import com.ssafy.questory.dto.request.plan.PlanDeleteRequestDto;
import com.ssafy.questory.dto.request.plan.PlanUpdateRequestDto;
import com.ssafy.questory.dto.response.plan.PlanInfoResponseDto;
import com.ssafy.questory.service.PlanService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/plans")
@RequiredArgsConstructor
public class PlanController {
    private final PlanService planService;

    @GetMapping()
    @Operation(summary = "계획 조회", description = "내 계획을 조회합니다.")
    public ResponseEntity<List<PlanInfoResponseDto>> getPlanInfo(
            @AuthenticationPrincipal(expression = "member") Member member) {
        return ResponseEntity.ok().body(planService.getPlanInfo(member));
    }

    @PostMapping()
    @Operation(summary = "계획 생성", description = "계획을 생성하고 경로를 추가합니다.")
    public ResponseEntity<Map<String, String>> create(
            @AuthenticationPrincipal(expression = "member") Member member,
            @RequestBody PlanCreateRequestDto planCreateRequestDto) {
        planService.create(member, planCreateRequestDto);
        return ResponseEntity.ok().body(Map.of(
                "message", "여행 계획이 생성되었습니다."
        ));
    }

    @PatchMapping()
    @Operation(summary = "계획 수정", description = "계획을 수정합니다.")
    public ResponseEntity<Map<String, String>> update(
            @AuthenticationPrincipal(expression = "member") Member member,
            @RequestBody PlanUpdateRequestDto planUpdateRequestDto) {
        planService.update(member, planUpdateRequestDto);
        return ResponseEntity.ok().body(Map.of(
                "message", "여행 계획이 수정되었습니다."
        ));
    }

    @DeleteMapping()
    @Operation(summary = "계획 삭제", description = "생성된 계획을 삭제합니다.")
    public ResponseEntity<Map<String, String>> delete(
            @AuthenticationPrincipal(expression = "member") Member member,
            @RequestBody PlanDeleteRequestDto planDeleteRequestDto) {
        planService.delete(member, planDeleteRequestDto);
        return ResponseEntity.ok().body(Map.of(
                "message", "여행 계획이 삭제되었습니다."
        ));
    }
}
