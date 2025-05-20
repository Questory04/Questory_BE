package com.ssafy.questory.dto.request.plan;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class PlanCreateRequestDto {
    private String title;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<RouteDto> routes;

    @Builder
    public PlanCreateRequestDto(String title, String description,
                                LocalDateTime startDate, LocalDateTime endDate,
                                List<RouteDto> routes) {
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.routes = routes;
    }

    @Getter
    public static class RouteDto {
        private Long attractionId;
        private int day;
        private int sequence;

        @Builder
        public RouteDto(Long attractionId, int day, int sequence) {
            this.attractionId = attractionId;
            this.day = day;
            this.sequence = sequence;
        }
    }
}
