package com.ssafy.questory.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SavedPlan {
    private String memberEmail;
    private Long planId;
    private LocalDateTime savedAt;

    protected SavedPlan() {}

    @Builder
    private SavedPlan(String memberEmail, Long planId, LocalDateTime savedAt) {
        this.memberEmail = memberEmail;
        this.planId = planId;
        this.savedAt = savedAt;
    }
}
