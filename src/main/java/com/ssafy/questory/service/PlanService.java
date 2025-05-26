package com.ssafy.questory.service;

import com.ssafy.questory.common.exception.CustomException;
import com.ssafy.questory.common.exception.ErrorCode;
import com.ssafy.questory.domain.Member;
import com.ssafy.questory.domain.Plan;
import com.ssafy.questory.domain.Route;
import com.ssafy.questory.dto.request.plan.PlanCreateRequestDto;
import com.ssafy.questory.dto.request.plan.PlanDeleteRequestDto;
import com.ssafy.questory.dto.request.plan.PlanUpdateRequestDto;
import com.ssafy.questory.dto.response.plan.PlanCreateResponseDto;
import com.ssafy.questory.dto.response.plan.PlanInfoResponseDto;
import com.ssafy.questory.dto.response.plan.PlansListResponseDto;
import com.ssafy.questory.repository.PlanRoutesRepository;
import com.ssafy.questory.repository.PlanRepository;
import com.ssafy.questory.repository.SavedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlanService {
    private final PlanRepository planRepository;
    private final PlanRoutesRepository planRoutesRepository;
    private final SavedRepository savedRepository;

    public List<PlanInfoResponseDto> getPlanInfo(Member member) {
        List<Plan> plans = planRepository.findByMemberEmail(member.getEmail());
        System.out.println(plans);
        return plans.stream()
                .map(plan -> {
                    List<Route> routes = planRoutesRepository.findByPlanId(plan.getPlanId());
                    return PlanInfoResponseDto.builder()
                            .planId(plan.getPlanId())
                            .memberEmail(plan.getMemberEmail())
                            .title(plan.getTitle())
                            .description(plan.getDescription())
                            .startDate(plan.getStartDate())
                            .endDate(plan.getEndDate())
                            .createdAt(plan.getCreatedAt())
                            .isStart(plan.isStart())
                            .isShared(plan.isShared())
                            .routes(routes)
                            .build();
                })
                .toList();
    }


    public PlanCreateResponseDto create(Member member, PlanCreateRequestDto dto) {
        Plan plan = Plan.builder()
                .memberEmail(member.getEmail())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .build();
        planRepository.create(plan);

//        List<Route> routes = dto.getRoutes().stream()
//                .map(routeDto -> Route.builder()
//                        .planId(plan.getPlanId())
//                        .attractionId(routeDto.getAttractionId())
//                        .day(routeDto.getDay())
//                        .sequence(routeDto.getSequence())
//                        .build())
//                .toList();
//
//        planRoutesRepository.insert(routes);

        long daysBetween = ChronoUnit.DAYS.between(plan.getStartDate().toLocalDate(), plan.getEndDate().toLocalDate())+1;
        return PlanCreateResponseDto.builder().planId(plan.getPlanId()).days(daysBetween).build();
    }

    public void update(Member member, PlanUpdateRequestDto planUpdateRequestDto) {
        Plan plan = planRepository.findById(planUpdateRequestDto.getPlanId())
                .orElseThrow(() -> new CustomException(ErrorCode.PLAN_NOT_FOUND));

        validateAuthorizedMember(plan.getMemberEmail(), member.getEmail());

        plan.update(planUpdateRequestDto.getTitle(), planUpdateRequestDto.getDescription(),
                planUpdateRequestDto.getStartDate(), planUpdateRequestDto.getEndDate());
        planRepository.update(plan);

        planRoutesRepository.deleteByPlanId(planUpdateRequestDto.getPlanId());

        if (planUpdateRequestDto.getRoutes() != null && !planUpdateRequestDto.getRoutes().isEmpty()) {
            List<Route> newRoutes = planUpdateRequestDto.getRoutes().stream()
                    .map(routeDto -> Route.builder()
                            .planId(planUpdateRequestDto.getPlanId())
                            .attractionId(routeDto.getAttractionId())
                            .day(routeDto.getDay())
                            .sequence(routeDto.getSequence())
                            .build())
                    .toList();

            planRoutesRepository.insert(newRoutes);
        }

    }


    public void delete(Member member, PlanDeleteRequestDto planDeleteRequestDto) {
        Plan plan = planRepository.findById(planDeleteRequestDto.getPlanId())
                .orElseThrow(() -> new CustomException(ErrorCode.PLAN_NOT_FOUND));

        validateAuthorizedMember(plan.getMemberEmail(), member.getEmail());

        planRoutesRepository.deleteByPlanId(planDeleteRequestDto.getPlanId());
        planRepository.deleteById(planDeleteRequestDto.getPlanId());
    }

    public void toggleShareStatus(Member member, Long planId) {
        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new CustomException(ErrorCode.PLAN_NOT_FOUND));

        validateAuthorizedMember(plan.getMemberEmail(), member.getEmail());

        boolean newStatus = !plan.isShared();
        planRepository.toggleShareStatus(planId, newStatus);
    }

    public void copy(Member member, Long planId) {
        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new CustomException(ErrorCode.PLAN_NOT_FOUND));

        if (!plan.isShared()) {
            throw new CustomException(ErrorCode.PLAN_NOT_SHARED);
        }
        if (member.getEmail().equals(plan.getMemberEmail())) {
            throw new CustomException(ErrorCode.CANNOT_COPY_OWN_PLAN);
        }
        if (savedRepository.findBySavedPlan(planId, member.getEmail()) != null) {
            throw new CustomException(ErrorCode.ALREADY_COPIED_PLAN);
        }
        savedRepository.copy(planId, member.getEmail());
    }

    private void validateAuthorizedMember(String email1, String email2) {
        if (!email1.equals(email2)) {
            throw new CustomException(ErrorCode.TOKEN_NOT_FOUND);
        }
    }

    public Optional<Plan> findPlanByPlanId(Long planId) {
        return planRepository.findById(planId);
    }

    public List<PlansListResponseDto> findPlansListCreatedByMe(String memberEmail) {
        return planRepository.findPlansListCreatedByMe(memberEmail);
    }

    public List<PlansListResponseDto> findPlansListCreatedByNotMe(String memberEmail) {
        return planRepository.findPlansListCreatedByNotMe(memberEmail);
    }

    public List<PlansListResponseDto> selectSharedPlansByMemberEmail(String memberEmail) {
        return planRepository.selectSharedPlansByMemberEmail(memberEmail);
    }
}
