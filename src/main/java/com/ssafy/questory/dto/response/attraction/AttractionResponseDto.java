package com.ssafy.questory.dto.response.attraction;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class AttractionResponseDto {
    private int attractionId;
    private String name;
    private String address;
}
