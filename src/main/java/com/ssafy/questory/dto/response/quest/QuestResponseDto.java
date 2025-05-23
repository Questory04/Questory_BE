package com.ssafy.questory.dto.response.quest;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
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

    @Override
    public String toString() {
        return "QuestResponseDto{" +
                "questId=" + questId +
                ", attractionId=" + attractionId +
                ", attractionName='" + attractionName + '\'' +
                ", attractionAddr='" + attractionAddr + '\'' +
                ", questTitle='" + questTitle + '\'' +
                ", questDifficulty='" + questDifficulty + '\'' +
                ", questDescription='" + questDescription + '\'' +
                ", stampDescription='" + stampDescription + '\'' +
                ", isPrivate=" + isPrivate +
                '}';
    }
}
