package com.ssafy.questory.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Route {
    private Long planId;
    private Long attractionId;
    private int day;
    private int sequence;

    protected Route() {}

    @Builder
    private Route(Long planId, Long attractionId, int day, int sequence) {
        this.planId = planId;
        this.attractionId = attractionId;
        this.day = day;
        this.sequence = sequence;
    }
}
