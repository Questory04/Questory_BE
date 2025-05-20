package com.ssafy.questory.dto.request.plan;

import com.ssafy.questory.domain.Route;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class PlanUpdateRequestDto {
    private Long planId;
    private String title;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<Route> routes;

    @Builder
    public PlanUpdateRequestDto(Long planId, String title, String description,
                                LocalDateTime startDate, LocalDateTime endDate, List<Route> routes) {
        this.planId = planId;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.routes = routes;
    }
}
