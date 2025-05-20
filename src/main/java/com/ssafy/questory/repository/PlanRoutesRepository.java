package com.ssafy.questory.repository;

import com.ssafy.questory.domain.Route;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PlanRoutesRepository {
    List<Route> findByPlanId(Long planId);
    void insert(@Param("routes") List<Route> routes);
    int deleteByPlanId(Long planId);
}
