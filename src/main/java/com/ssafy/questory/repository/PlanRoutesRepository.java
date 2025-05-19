package com.ssafy.questory.repository;

import com.ssafy.questory.domain.Route;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PlanRoutesRepository {
    void insert(@Param("routes") List<Route> routes);
}
