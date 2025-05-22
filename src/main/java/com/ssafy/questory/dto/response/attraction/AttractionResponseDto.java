package com.ssafy.questory.dto.response.attraction;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AttractionResponseDto {
    private int attractionId;
    private String name;
    private String address;

    @Override
    public String toString() {
        return "AttractionResponseDto{" +
                "attractionId=" + attractionId +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
