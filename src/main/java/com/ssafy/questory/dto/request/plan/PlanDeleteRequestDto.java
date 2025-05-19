package com.ssafy.questory.dto.request.plan;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PlanDeleteRequestDto {
    private Long planId;

    @Builder
    public PlanDeleteRequestDto(Long planId) {
        this.planId = planId;
    }
}
