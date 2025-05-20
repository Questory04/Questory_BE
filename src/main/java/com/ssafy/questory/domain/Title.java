package com.ssafy.questory.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Title {
    private Long titleId;
    private String name;
    private LocalDateTime createdAt;

    protected Title() {}

    @Builder
    private Title(Long titleId, String name, LocalDateTime createdAt) {
        this.titleId = titleId;
        this.name = name;
        this.createdAt = createdAt;
    }
}
