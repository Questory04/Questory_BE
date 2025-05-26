package com.ssafy.questory.service;

import com.ssafy.questory.domain.Member;
import com.ssafy.questory.dto.request.route.RouteCreateRequestDto;
import com.ssafy.questory.dto.response.route.RouteResponseDto;
import com.ssafy.questory.repository.RouteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class RouteService {
    private final RouteRepository routeRepository;

    public void createRoute(Member member, RouteCreateRequestDto routeCreateRequestDto) {
        routeRepository.createRoute(routeCreateRequestDto);
        routeRepository.createRoutePlan(routeCreateRequestDto);
    }

    public List<RouteResponseDto> getRoutesByPlanId(String memberEmail, int planId) {
        // 사용자가 만든 계획이 맞는지 확인하기

        // 계획이 존재하는지 확인하기

        // 경로 조회하기
        return routeRepository.getRoutesByPlanId(planId);
    }
}
