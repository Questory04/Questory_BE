package com.ssafy.questory.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class Plan {
    private Long planId;
    private String memberEmail;
    private String title;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime createdAt;
    private boolean isStart;
    private boolean isShared;

    protected Plan() {}

    @Builder
    private Plan(Long planId, String memberEmail, String title, String description,
                 LocalDateTime startDate, LocalDateTime endDate, LocalDateTime createdAt,
                 boolean isStart, boolean isShared) {
        this.planId = planId;
        this.memberEmail = memberEmail;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.createdAt = createdAt;
        this.isStart = isStart;
        this.isShared = isShared;
    }

    public void update(String title, String description, LocalDateTime startDate, LocalDateTime endDate) {
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
