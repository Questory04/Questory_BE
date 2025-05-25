package com.ssafy.questory.dto.response.quest;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@ToString
public class QuestsResponseDto {
    private int questId;
    private String questTitle;
    private String attractionImage;
    private String attractionTitle;
    private String contentTypeName;
    private String attractionAddress;
    private String questDifficulty;
    private String questDescription;
    private Double attractionLatitude;
    private Double attractionLongitude;
}
