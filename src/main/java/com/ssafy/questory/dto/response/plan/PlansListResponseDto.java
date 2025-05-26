package com.ssafy.questory.dto.response.plan;

import com.ssafy.questory.domain.PlanStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
public class PlansListResponseDto {
    private int planId;
    private String planTitle;
    private String planDescription;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private int duration;
    private PlanStatus status;
    private int routesCnt;
    private int completionRate;
    private LocalDateTime createdAt;
}
