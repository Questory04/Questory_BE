package com.ssafy.questory.dto.response.stamp;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@ToString
public class StampsResponseDto {

    private String title;
    private String contentTypeId;
    private LocalDate date;
    private String contentTypeName;
    private String sidoName;
    private String difficulty;
    private String description;
}
