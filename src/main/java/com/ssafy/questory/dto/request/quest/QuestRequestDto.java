package com.ssafy.questory.dto.request.quest;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class QuestRequestDto {
    private int attractionId;
    private String title;
    private String questDescription;
    private String difficulty;
    private Boolean isPrivate;
    private String stampDescription;
}
