package com.ssafy.questory.dto.response.plan;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class PlanCreateResponseDto {
    private Long planId;
    private Long days;
}
