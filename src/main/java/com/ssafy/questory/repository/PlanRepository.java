package com.ssafy.questory.repository;

import com.ssafy.questory.domain.Plan;
import com.ssafy.questory.dto.response.plan.PlansListResponseDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface PlanRepository {
    List<Plan> findByMemberEmail(String memberEmail);
    Optional<Plan> findById(Long planId);
    int create(Plan plan);
    int update(Plan plan);
    int toggleShareStatus(@Param("planId") Long planId, @Param("shared") boolean isShared);
    int deleteById(Long planId);
    List<PlansListResponseDto> findPlansListCreatedByMe(@Param("memberEmail") String memberEmail);
    List<PlansListResponseDto> findPlansListCreatedByNotMe(@Param("memberEmail") String memberEmail);
    List<PlansListResponseDto> selectSharedPlansByMemberEmail(@Param("memberEmail") String memberEmail);
}
