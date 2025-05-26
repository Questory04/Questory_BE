package com.ssafy.questory.repository;

import com.ssafy.questory.dto.request.route.RouteCreateRequestDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RouteRepository {
    void createRoute(RouteCreateRequestDto routeCreateRequestDto);
    void createRoutePlan(RouteCreateRequestDto routeCreateRequestDto);
}
