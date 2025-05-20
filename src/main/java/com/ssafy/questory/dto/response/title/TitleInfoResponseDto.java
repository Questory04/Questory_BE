package com.ssafy.questory.dto.response.title;

import com.ssafy.questory.domain.Title;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TitleInfoResponseDto {
    private Long titleId;
    private String name;
    private LocalDateTime createdAt;

    @Builder
    private TitleInfoResponseDto(Long titleId, String name, LocalDateTime createdAt) {
        this.titleId = titleId;
        this.name = name;
        this.createdAt = createdAt;
    }

    public static TitleInfoResponseDto from(Title title) {
        return TitleInfoResponseDto.builder()
                .titleId(title.getTitleId())
                .name(title.getName())
                .createdAt(title.getCreatedAt())
                .build();
    }
}
