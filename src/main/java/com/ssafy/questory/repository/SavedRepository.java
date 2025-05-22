package com.ssafy.questory.repository;

import com.ssafy.questory.domain.SavedPlan;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SavedRepository {
    SavedPlan findBySavedPlan(Long planId, String memberEmail);
    int copy(Long planId, String memberEmail);
}
