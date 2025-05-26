package com.ssafy.questory.repository;

import com.ssafy.questory.dto.request.route.RouteCreateRequestDto;
import com.ssafy.questory.dto.response.route.RouteResponseDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RouteRepository {
    void createRoute(RouteCreateRequestDto routeCreateRequestDto);
    void createRoutePlan(RouteCreateRequestDto routeCreateRequestDto);

    List<RouteResponseDto> getRoutesByPlanId(@Param("planId") int planId);

}
