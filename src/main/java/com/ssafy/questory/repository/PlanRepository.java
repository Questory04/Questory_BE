package com.ssafy.questory.repository;

import com.ssafy.questory.domain.Plan;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PlanRepository {
    int create(Plan plan);
}
