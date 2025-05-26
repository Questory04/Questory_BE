package com.ssafy.questory.controller;

import com.ssafy.questory.domain.Member;
import com.ssafy.questory.domain.Plan;
import com.ssafy.questory.dto.request.plan.PlanCreateRequestDto;
import com.ssafy.questory.dto.request.plan.PlanDeleteRequestDto;
import com.ssafy.questory.dto.request.plan.PlanUpdateRequestDto;
import com.ssafy.questory.dto.response.plan.PlanCreateResponseDto;
import com.ssafy.questory.dto.response.plan.PlanInfoResponseDto;
import com.ssafy.questory.service.PlanService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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
    public ResponseEntity<Map<String, Object>> create(
            @AuthenticationPrincipal(expression = "member") Member member,
            @RequestBody PlanCreateRequestDto planCreateRequestDto) {
        PlanCreateResponseDto planCreateResponseDto = planService.create(member, planCreateRequestDto);

        Map<String, Object> response = new HashMap<>();
        response.put("plan", planCreateResponseDto);

        return ResponseEntity.status(HttpStatus.OK).body(response);

//        return ResponseEntity.ok().body(Map.of(
//                "message", "여행 계획이 생성되었습니다."
//        ));
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

    @PatchMapping("/{planId}/share/toggle")
    @Operation(summary = "공유 상태 토글", description = "공유 상태를 반전시킵니다.")
    public ResponseEntity<Map<String, String>> toggleShareStatus(
            @AuthenticationPrincipal(expression = "member") Member member,
            @PathVariable Long planId) {
        planService.toggleShareStatus(member, planId);
        return ResponseEntity.ok(Map.of("message", "공유 상태가 변경되었습니다."));
    }

    @PostMapping("/{planId}/copy")
    @Operation(summary = "계획 복사", description = "공유된 계획을 복사합니다.")
    public ResponseEntity<Map<String, String>> copy(
            @AuthenticationPrincipal (expression = "member") Member member,
            @PathVariable Long planId) {
        planService.copy(member, planId);
        return ResponseEntity.ok(Map.of("message", "계획이 저장되었습니다."));
    }
}
