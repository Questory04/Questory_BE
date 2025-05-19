package com.ssafy.questory.service;

import com.ssafy.questory.domain.Member;
import com.ssafy.questory.domain.Plan;
import com.ssafy.questory.domain.Route;
import com.ssafy.questory.dto.request.plan.PlanCreateRequestDto;
import com.ssafy.questory.repository.PlanRoutesRepository;
import com.ssafy.questory.repository.PlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlanService {
    private final PlanRepository planRepository;
    private final PlanRoutesRepository planRoutesRepository;

    public void create(Member member, PlanCreateRequestDto dto) {
        // 1. 플랜 생성
        Plan plan = Plan.builder()
                .memberEmail(member.getEmail())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .build();
        planRepository.create(plan); // planId가 plan에 자동 할당

        // 2. Route 리스트 생성
        List<Route> routes = dto.getRoutes().stream()
                .map(routeDto -> Route.builder()
                        .planId(plan.getPlanId())
                        .attractionId(routeDto.getAttractionId())
                        .day(routeDto.getDay())
                        .sequence(routeDto.getSequence())
                        .build())
                .toList();

        // 3. Bulk Insert
        planRoutesRepository.insert(routes);
    }
}
