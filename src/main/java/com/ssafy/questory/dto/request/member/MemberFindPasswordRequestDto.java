package com.ssafy.questory.dto.request.member;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberFindPasswordRequestDto {
    private String email;

    @Builder
    private MemberFindPasswordRequestDto(String email) {
        this.email = email;
    }
}
