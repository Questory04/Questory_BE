package com.ssafy.questory.controller;

import com.ssafy.questory.common.exception.CustomException;
import com.ssafy.questory.common.exception.ErrorCode;
import com.ssafy.questory.config.jwt.JwtService;
import com.ssafy.questory.domain.Member;
import com.ssafy.questory.dto.request.plan.PlanCreateRequestDto;
import com.ssafy.questory.dto.request.route.RouteCreateRequestDto;
import com.ssafy.questory.dto.response.plan.PlanCreateResponseDto;
import com.ssafy.questory.dto.response.quest.QuestsResponseDto;
import com.ssafy.questory.dto.response.route.RouteResponseDto;
import com.ssafy.questory.service.QuestService;
import com.ssafy.questory.service.RouteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/routes")
@RequiredArgsConstructor
@Tag(name = "루트 API", description = "루트 관련 API")
public class RouteController {
    private final RouteService routeService;
    private final JwtService jwtService;

    @PostMapping()
    @Operation(summary = "루트 생성", description = "경로을 생성하고 계획에 추가합니다.")
    public ResponseEntity<Map<String, Object>> createRoute(
            @AuthenticationPrincipal(expression = "member") Member member,
            @RequestBody List<RouteCreateRequestDto> routeCreateRequestDtoList) {
        for(RouteCreateRequestDto dto : routeCreateRequestDtoList){
            routeService.createRoute(member, dto);
        }

        return ResponseEntity.ok().body(Map.of(
                "message", "여행 루트가 생성되었습니다."
        ));
    }

    @GetMapping("/{planId}")
    @Operation(summary = "계획에 속한 관광지 목록 조회", description = "계획에 속한 관광지 목록 조회합니다.")
    public ResponseEntity<Map<String, Object>> getRoutesByPlanId(@PathVariable int planId,
                                                          @RequestHeader(value = "Authorization", required = false) String authorizationHeader) {
        if(authorizationHeader == null){
            throw new CustomException(ErrorCode.TOKEN_NOT_FOUND);
        } else if(!authorizationHeader.startsWith("Bearer ")){
            throw new CustomException(ErrorCode.INVALID_TOKEN_FORMAT);
        }

        String token = authorizationHeader.substring(7);
        String memberEmail = jwtService.extractUsername(token);

        List<RouteResponseDto> routeResponseDtoList = routeService.getRoutesByPlanId(memberEmail, planId);

        Map<String, Object> response = new HashMap<>();
        response.put("routes", routeResponseDtoList);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
