package com.ssafy.questory.dto.request.title;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TitleEarnRequestDto {
    private Long titleId;

    @Builder
    private TitleEarnRequestDto(Long titleId) {
        this.titleId = titleId;
    }
}
