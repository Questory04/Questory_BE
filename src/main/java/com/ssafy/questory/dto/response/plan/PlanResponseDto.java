package com.ssafy.questory.dto.response.plan;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
//@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PlanResponseDto {
    private String title;
    private int totalDays;
    private String destination;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String description;
}
