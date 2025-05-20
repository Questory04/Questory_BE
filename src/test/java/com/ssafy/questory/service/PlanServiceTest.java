package com.ssafy.questory.service;

import com.ssafy.questory.common.exception.CustomException;
import com.ssafy.questory.common.exception.ErrorCode;
import com.ssafy.questory.domain.Member;
import com.ssafy.questory.domain.Plan;
import com.ssafy.questory.domain.Route;
import com.ssafy.questory.dto.request.plan.PlanCreateRequestDto;
import com.ssafy.questory.dto.request.plan.PlanDeleteRequestDto;
import com.ssafy.questory.dto.response.plan.PlanInfoResponseDto;
import com.ssafy.questory.repository.PlanRepository;
import com.ssafy.questory.repository.PlanRoutesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PlanServiceTest {

    private PlanRepository planRepository;
    private PlanRoutesRepository planRoutesRepository;
    private PlanService planService;

    @BeforeEach
    void setUp() {
        planRepository = mock(PlanRepository.class);
        planRoutesRepository = mock(PlanRoutesRepository.class);
        planService = new PlanService(planRepository, planRoutesRepository);
    }

    @Test
    @DisplayName("getPlanInfo: 회원의 모든 계획과 각 계획의 경로 목록을 조회한다")
    void getPlanInfoTest() {
        // given
        Member member = Member.builder().email("test@domain.com").build();

        Plan plan1 = Plan.builder()
                .planId(1L)
                .memberEmail(member.getEmail())
                .title("여행1")
                .description("설명1")
                .startDate(LocalDateTime.of(2025, 5, 20, 10, 0))
                .endDate(LocalDateTime.of(2025, 5, 21, 20, 0))
                .createdAt(LocalDateTime.of(2025, 5, 19, 9, 0))
                .isStart(false)
                .build();

        Plan plan2 = Plan.builder()
                .planId(2L)
                .memberEmail(member.getEmail())
                .title("여행2")
                .description("설명2")
                .startDate(LocalDateTime.of(2025, 6, 1, 8, 0))
                .endDate(LocalDateTime.of(2025, 6, 2, 22, 0))
                .createdAt(LocalDateTime.of(2025, 5, 25, 12, 0))
                .isStart(true)
                .build();

        Route route1 = Route.builder().planId(1L).attractionId(101L).day(1).sequence(1).build();
        Route route2 = Route.builder().planId(1L).attractionId(102L).day(1).sequence(2).build();
        Route route3 = Route.builder().planId(2L).attractionId(201L).day(2).sequence(1).build();

        when(planRepository.findByMemberEmail(member.getEmail()))
                .thenReturn(List.of(plan1, plan2));

        when(planRoutesRepository.findByPlanId(1L))
                .thenReturn(List.of(route1, route2));
        when(planRoutesRepository.findByPlanId(2L))
                .thenReturn(List.of(route3));

        // when
        List<PlanInfoResponseDto> result = planService.getPlanInfo(member);

        // then
        assertThat(result).hasSize(2);

        PlanInfoResponseDto planDto1 = result.get(0);
        assertThat(planDto1.getPlanId()).isEqualTo(1L);
        assertThat(planDto1.getRoutes()).hasSize(2);
        assertThat(planDto1.getRoutes().get(0).getAttractionId()).isEqualTo(101L);

        PlanInfoResponseDto planDto2 = result.get(1);
        assertThat(planDto2.getPlanId()).isEqualTo(2L);
        assertThat(planDto2.getRoutes()).hasSize(1);
        assertThat(planDto2.getRoutes().get(0).getAttractionId()).isEqualTo(201L);

        verify(planRepository).findByMemberEmail(member.getEmail());
        verify(planRoutesRepository).findByPlanId(1L);
        verify(planRoutesRepository).findByPlanId(2L);
    }

    @Test
    @DisplayName("create: 회원의 계획 및 경로를 저장한다")
    void createTest() {
        // given
        Member member = Member.builder().email("test@domain.com").build();

        // PlanCreateRequestDto.RouteDto를 빌더로 생성
        PlanCreateRequestDto.RouteDto routeDto1 = PlanCreateRequestDto.RouteDto.builder()
                .attractionId(101L).day(1).sequence(1).build();
        PlanCreateRequestDto.RouteDto routeDto2 = PlanCreateRequestDto.RouteDto.builder()
                .attractionId(102L).day(1).sequence(2).build();

        PlanCreateRequestDto dto = PlanCreateRequestDto.builder()
                .title("여행1")
                .description("설명1")
                .startDate(LocalDateTime.of(2025, 5, 20, 10, 0))
                .endDate(LocalDateTime.of(2025, 5, 21, 20, 0))
                .routes(List.of(routeDto1, routeDto2))
                .build();

        // PlanRepository.create(plan) 실행 시, plan에 id가 채워지도록 모킹
        doAnswer(invocation -> {
            Plan plan = invocation.getArgument(0);
            // 실제로는 plan.setPlanId(1L) 등을 할 수 있는데, builder 패턴이면 불가. insert 호출만 검증!
            return 1;
        }).when(planRepository).create(any(Plan.class));

        // when
        planService.create(member, dto);

        // then
        verify(planRepository).create(any(Plan.class));
        verify(planRoutesRepository).insert(anyList());
    }


    @Test
    @DisplayName("delete: 권한 있는 회원이 계획을 정상적으로 삭제한다")
    void deleteTest() {
        // given
        Member member = Member.builder().email("test@domain.com").build();
        long planId = 1L;
        Plan plan = Plan.builder()
                .planId(planId)
                .memberEmail(member.getEmail())
                .build();

        PlanDeleteRequestDto dto = PlanDeleteRequestDto.builder()
                .planId(planId)
                .build();

        when(planRepository.findById(planId)).thenReturn(Optional.of(plan));

        // when
        planService.delete(member, dto);

        // then
        verify(planRoutesRepository).deleteByPlanId(planId);
        verify(planRepository).deleteById(planId);
    }

    @Test
    @DisplayName("delete: 다른 사용자가 계획 삭제를 시도하면 예외가 발생한다")
    void delete_throwsExceptionWhenUnauthorized() {
        // given
        Member member = Member.builder().email("user1@domain.com").build();
        long planId = 1L;
        // plan.owner와 member.email 다름
        Plan plan = Plan.builder()
                .planId(planId)
                .memberEmail("user2@domain.com")
                .build();

        PlanDeleteRequestDto dto = PlanDeleteRequestDto.builder()
                .planId(planId)
                .build();

        when(planRepository.findById(planId)).thenReturn(Optional.of(plan));

        // when, then
        assertThatThrownBy(() -> planService.delete(member, dto))
                .isInstanceOf(CustomException.class)
                .hasMessageContaining(ErrorCode.TOKEN_NOT_FOUND.getMessage());

        // deleteByPlanId, deleteById는 호출되면 안 됨
        verify(planRoutesRepository, never()).deleteByPlanId(anyLong());
        verify(planRepository, never()).deleteById(anyLong());
    }

    @Test
    @DisplayName("delete: 없는 계획을 삭제하려 하면 예외가 발생한다")
    void delete_throwsExceptionWhenNotFound() {
        // given
        Member member = Member.builder().email("user1@domain.com").build();
        long planId = 1L;

        PlanDeleteRequestDto dto = PlanDeleteRequestDto.builder()
                .planId(planId)
                .build();

        when(planRepository.findById(planId)).thenReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> planService.delete(member, dto))
                .isInstanceOf(CustomException.class)
                .hasMessageContaining(ErrorCode.PLAN_NOT_FOUND.getMessage());

        verify(planRoutesRepository, never()).deleteByPlanId(anyLong());
        verify(planRepository, never()).deleteById(anyLong());
    }
}
