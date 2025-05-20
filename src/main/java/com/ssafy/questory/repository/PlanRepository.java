package com.ssafy.questory.repository;

import com.ssafy.questory.domain.Plan;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface PlanRepository {
    List<Plan> findByMemberEmail(String memberEmail);
    Optional<Plan> findById(Long planId);
    int create(Plan plan);
    int update(Plan plan);
    int deleteById(Long planId);
}
