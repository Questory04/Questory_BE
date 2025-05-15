package com.ssafy.questory.dto.request.member;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberEmailRequestDto {
    private String email;

    @Builder
    private MemberEmailRequestDto(String email) {
        this.email = email;
    }
}
