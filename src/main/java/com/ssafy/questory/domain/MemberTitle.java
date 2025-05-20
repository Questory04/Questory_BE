package com.ssafy.questory.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MemberTitle {
    private String memberEmail;
    private Long titleId;
    private LocalDateTime createdAt;

    protected MemberTitle() {}

    @Builder
    private MemberTitle(String memberEmail, Long titleId, LocalDateTime createdAt) {
        this.memberEmail = memberEmail;
        this.titleId = titleId;
        this.createdAt = createdAt;
    }
}
