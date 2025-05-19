package com.ssafy.questory.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class Plan {
    private Long planId;
    private String memberEmail;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate createdAt;
    private boolean isStart;

    protected Plan() {}

    @Builder
    private Plan(Long planId, String memberEmail, String title, String description,
                 LocalDate startDate, LocalDate endDate) {
        this.planId = planId;
        this.memberEmail = memberEmail;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
