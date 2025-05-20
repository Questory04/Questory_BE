package com.ssafy.questory.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Title {
    private Long titleId;
    private String name;

    protected Title() {}

    @Builder
    private Title(Long titleId, String name) {
        this.titleId = titleId;
        this.name = name;
    }
}
