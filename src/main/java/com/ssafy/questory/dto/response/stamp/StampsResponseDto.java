package com.ssafy.questory.dto.response.stamp;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class StampsResponseDto {

    private String title;
    private String contentTypeId;
    private LocalDate date;
    private String contentTypeName;
    private String sidoName;
    private String difficulty;
    private String description;

    @Override
    public String toString() {
        return "StampsResponseDto{" +
                "title='" + title + '\'' +
                ", contentTypeId='" + contentTypeId + '\'' +
                ", date=" + date +
                ", contentTypeName='" + contentTypeName + '\'' +
                ", sidoName='" + sidoName + '\'' +
                ", difficulty='" + difficulty + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
