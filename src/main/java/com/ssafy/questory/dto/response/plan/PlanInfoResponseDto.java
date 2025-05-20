package com.ssafy.questory.dto.response.plan;

import com.ssafy.questory.domain.Route;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class PlanInfoResponseDto {
    private Long planId;
    private String memberEmail;
    private String title;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime createdAt;
    private boolean isStart;
    private List<Route> routes;

    @Builder
    private PlanInfoResponseDto(Long planId, String memberEmail, String title, String description,
                                LocalDateTime startDate, LocalDateTime endDate, LocalDateTime createdAt, boolean isStart,
                                List<Route> routes) {
        this.planId = planId;
        this.memberEmail = memberEmail;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.createdAt = createdAt;
        this.isStart = isStart;
        this.routes = routes;
    }
}
