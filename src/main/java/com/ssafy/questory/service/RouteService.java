package com.ssafy.questory.service;

import com.ssafy.questory.domain.Member;
import com.ssafy.questory.dto.request.route.RouteCreateRequestDto;
import com.ssafy.questory.repository.RouteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class RouteService {
    private final RouteRepository routeRepository;

    public void createRoute(Member member, RouteCreateRequestDto routeCreateRequestDto) {
        routeRepository.createRoute(routeCreateRequestDto);
        routeRepository.createRoutePlan(routeCreateRequestDto);
    }
}
