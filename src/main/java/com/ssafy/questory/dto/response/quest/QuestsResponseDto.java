package com.ssafy.questory.dto.response.quest;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class QuestsResponseDto {
    private String questTitle;
    private String attractionImage;
    private String attractionTitle;
    private String contentTypeName;
    private String attractionAddress;
    private String questDifficulty;
    private String questDescription;

    @Override
    public String toString() {
        return "QuestsResponseDto{" +
                "questTitle='" + questTitle + '\'' +
                ", attractionImage='" + attractionImage + '\'' +
                ", attractionTitle='" + attractionTitle + '\'' +
                ", contentTypeName='" + contentTypeName + '\'' +
                ", attractionAddress='" + attractionAddress + '\'' +
                ", questDifficulty='" + questDifficulty + '\'' +
                ", questDescription='" + questDescription + '\'' +
                '}';
    }
}
