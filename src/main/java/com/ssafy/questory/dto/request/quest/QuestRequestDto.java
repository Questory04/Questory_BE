package com.ssafy.questory.dto.request.quest;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class QuestRequestDto {
    private int attractionId;
    private String title;
    private String questDescription;
    private String difficulty;
    private Boolean isPrivate;
    private String stampImageUrl;
    private String stampDescription;

    @Override
    public String toString() {
        return "QuestRequestDto{" +
                "attractionId=" + attractionId +
                ", title='" + title + '\'' +
                ", questDescription='" + questDescription + '\'' +
                ", difficulty='" + difficulty + '\'' +
                ", isPrivate=" + isPrivate +
                ", stampImageUrl='" + stampImageUrl + '\'' +
                ", stampDescription='" + stampDescription + '\'' +
                '}';
    }
}
