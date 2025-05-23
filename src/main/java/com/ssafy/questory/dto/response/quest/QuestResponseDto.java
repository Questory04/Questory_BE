package com.ssafy.questory.dto.response.quest;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class QuestResponseDto {
    private int questId;
    private int attractionId;
    private String attractionName;
    private String attractionAddr;
    private String questTitle;
    private String questDifficulty;
    private String questDescription;
    private String stampDescription;
    private boolean isPrivate;
}
