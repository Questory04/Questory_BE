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
public class QuestPositionRequestDto {
    private Double attractionLatitude;
    private Double attractionLongitude;
    private Double userLatitude;
    private Double userLongitude;
}
