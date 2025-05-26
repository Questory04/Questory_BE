package com.ssafy.questory.controller;

import com.ssafy.questory.domain.Member;
import com.ssafy.questory.dto.request.plan.PlanCreateRequestDto;
import com.ssafy.questory.dto.request.route.RouteCreateRequestDto;
import com.ssafy.questory.dto.response.plan.PlanCreateResponseDto;
import com.ssafy.questory.dto.response.route.RouteCreateResponseDto;
import com.ssafy.questory.service.QuestService;
import com.ssafy.questory.service.RouteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/routes")
@RequiredArgsConstructor
@Tag(name = "루트 API", description = "루트 관련 API")
public class RouteController {
    private final RouteService routeService;

    @PostMapping()
    @Operation(summary = "루트 생성", description = "경로을 생성하고 계획에 추가합니다.")
    public ResponseEntity<Map<String, Object>> createRoute(
            @AuthenticationPrincipal(expression = "member") Member member,
            @RequestBody List<RouteCreateRequestDto> routeCreateRequestDtoList) {
        for(RouteCreateRequestDto dto : routeCreateRequestDtoList){
            routeService.createRoute(member, dto);
        }

//        Map<String, Object> response = new HashMap<>();
//        response.put("route", routeCreateResponseDto);
//
//        return ResponseEntity.status(HttpStatus.OK).body(response);

        return ResponseEntity.ok().body(Map.of(
                "message", "여행 루트가 생성되었습니다."
        ));
    }

}
