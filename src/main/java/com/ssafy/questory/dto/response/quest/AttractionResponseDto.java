package com.ssafy.questory.dto.response.quest;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AttractionResponseDto {
    private String name;
    private String address;

    @Override
    public String toString() {
        return "AttractionResponseDto{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
