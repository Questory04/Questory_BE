package com.ssafy.questory.controller;

import com.ssafy.questory.common.exception.CustomException;
import com.ssafy.questory.common.exception.ErrorCode;
import com.ssafy.questory.config.jwt.JwtService;
import com.ssafy.questory.domain.Member;
import com.ssafy.questory.domain.Plan;
import com.ssafy.questory.dto.request.plan.PlanCreateRequestDto;
import com.ssafy.questory.dto.request.plan.PlanDeleteRequestDto;
import com.ssafy.questory.dto.request.plan.PlanUpdateRequestDto;
import com.ssafy.questory.dto.response.plan.PlanCreateResponseDto;
import com.ssafy.questory.dto.response.plan.PlanInfoResponseDto;
import com.ssafy.questory.dto.response.plan.PlansListResponseDto;
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
import java.util.Optional;

@RestController
@RequestMapping("/plans")
@RequiredArgsConstructor
public class PlanController {
    private final PlanService planService;
    private final JwtService jwtService;

    @GetMapping()
    @Operation(summary = "계획 조회", description = "내 계획을 조회합니다.")
    public ResponseEntity<List<PlanInfoResponseDto>> getPlanInfo(
            @AuthenticationPrincipal(expression = "member") Member member) {
        return ResponseEntity.ok().body(planService.getPlanInfo(member));
    }

    @GetMapping("/{planId}")
    @Operation(summary = "계획 상세 정보 조회", description = "계획 상세 정보를 조회합니다.")
    public Optional<Plan> findPlanByPlanId(@PathVariable Long planId,
                                           @RequestHeader(value = "Authorization", required = false) String authorizationHeader) {
        if(authorizationHeader == null){
            throw new CustomException(ErrorCode.TOKEN_NOT_FOUND);
        } else if(!authorizationHeader.startsWith("Bearer ")){
            throw new CustomException(ErrorCode.INVALID_TOKEN_FORMAT);
        }

//        String token = authorizationHeader.substring(7);
//        String memberEmail = jwtService.extractUsername(token);

        return planService.findPlanByPlanId(planId);
    }


    @GetMapping("/me/created")
    @Operation(summary = "생성한 여행 계획 목록 조회", description = "생성한 여행 계획 목록을 조회합니다.")
    public ResponseEntity<List<PlansListResponseDto>> findPlansListCreatedByMe(@RequestHeader(value = "Authorization", required = false) String authorizationHeader) {
        if(authorizationHeader == null){
            throw new CustomException(ErrorCode.TOKEN_NOT_FOUND);
        } else if(!authorizationHeader.startsWith("Bearer ")){
            throw new CustomException(ErrorCode.INVALID_TOKEN_FORMAT);
        }

        String token = authorizationHeader.substring(7);
        String memberEmail = jwtService.extractUsername(token);

        List<PlansListResponseDto> plansListResponseDtoList = planService.findPlansListCreatedByMe(memberEmail);

        return ResponseEntity.status(HttpStatus.OK).body(plansListResponseDtoList);
    }

    @GetMapping("/all")
    @Operation(summary = "모든 여행 계획 목록 조회", description = "모든 여행 계획 목록을 조회합니다.")
    public ResponseEntity<List<PlansListResponseDto>> findPlansListCreatedByNotMe(@RequestHeader(value = "Authorization", required = false) String authorizationHeader) {
        if(authorizationHeader == null){
            throw new CustomException(ErrorCode.TOKEN_NOT_FOUND);
        } else if(!authorizationHeader.startsWith("Bearer ")){
            throw new CustomException(ErrorCode.INVALID_TOKEN_FORMAT);
        }

        String token = authorizationHeader.substring(7);
        String memberEmail = jwtService.extractUsername(token);

        List<PlansListResponseDto> plansListResponseDtoList = planService.findPlansListCreatedByNotMe(memberEmail);

        return ResponseEntity.status(HttpStatus.OK).body(plansListResponseDtoList);
    }

    @GetMapping("/shared")
    @Operation(summary = "공유 받은 여행 계획 목록 조회", description = "공유 받은 여행 계획 목록을 조회합니다.")
    public ResponseEntity<List<PlansListResponseDto>> findSharedPlansListCreatedByMe(@RequestHeader(value = "Authorization", required = false) String authorizationHeader) {
        if(authorizationHeader == null){
            throw new CustomException(ErrorCode.TOKEN_NOT_FOUND);
        } else if(!authorizationHeader.startsWith("Bearer ")){
            throw new CustomException(ErrorCode.INVALID_TOKEN_FORMAT);
        }

        String token = authorizationHeader.substring(7);
        String memberEmail = jwtService.extractUsername(token);

        List<PlansListResponseDto> plansListResponseDtoList = planService.selectSharedPlansByMemberEmail(memberEmail);

        return ResponseEntity.status(HttpStatus.OK).body(plansListResponseDtoList);
    }


    @PostMapping()
    @Operation(summary = "계획 생성", description = "계획을 생성합니다.")
    public ResponseEntity<Map<String, Object>> create(
            @AuthenticationPrincipal(expression = "member") Member member,
            @RequestBody PlanCreateRequestDto planCreateRequestDto) {
        PlanCreateResponseDto planCreateResponseDto = planService.create(member, planCreateRequestDto);

        Map<String, Object> response = new HashMap<>();
        response.put("plan", planCreateResponseDto);

        return ResponseEntity.status(HttpStatus.OK).body(response);
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
