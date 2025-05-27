package com.ssafy.questory.dto.response.quest;

import com.ssafy.questory.domain.DifficultyStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class QuestRecommendationResponseDto {
    private Long questId;
    private String title;
    private String description;
    private String difficulty;
    private String img;
    private int exp;
    private int participantCount;
    private LocalDateTime createdAt;

    @Builder
    private QuestRecommendationResponseDto(Long questId, String title, String description,
                                           String difficulty, String img, int exp, int participantCount, LocalDateTime createdAt) {
        this.questId = questId;
        this.title = title;
        this.description = description;
        this.difficulty = difficulty;
        this.img = img;
        this.exp = exp;
        this.participantCount = participantCount;
        this.createdAt = createdAt;
    }
}
