package com.ssafy.questory.service;

import com.ssafy.questory.common.exception.CustomException;
import com.ssafy.questory.common.exception.ErrorCode;
import com.ssafy.questory.domain.Member;
import com.ssafy.questory.domain.Plan;
import com.ssafy.questory.domain.Route;
import com.ssafy.questory.dto.request.plan.PlanCreateRequestDto;
import com.ssafy.questory.dto.request.plan.PlanDeleteRequestDto;
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
        Plan plan = Plan.builder()
                .memberEmail(member.getEmail())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .build();
        planRepository.create(plan);

        List<Route> routes = dto.getRoutes().stream()
                .map(routeDto -> Route.builder()
                        .planId(plan.getPlanId())
                        .attractionId(routeDto.getAttractionId())
                        .day(routeDto.getDay())
                        .sequence(routeDto.getSequence())
                        .build())
                .toList();

        planRoutesRepository.insert(routes);
    }

    public void delete(Member member, PlanDeleteRequestDto planDeleteRequestDto) {
        Plan plan = planRepository.findById(planDeleteRequestDto.getPlanId())
                .orElseThrow(() -> new CustomException(ErrorCode.PLAN_NOT_FOUND));

        validateAuthorizedMember(plan.getMemberEmail(), member.getEmail());

        planRoutesRepository.deleteByPlanId(planDeleteRequestDto.getPlanId());
        planRepository.deleteById(planDeleteRequestDto.getPlanId());
    }

    private void validateAuthorizedMember(String email1, String email2) {
        if (!email1.equals(email2)) {
            throw new CustomException(ErrorCode.TOKEN_NOT_FOUND);
        }
    }
}
