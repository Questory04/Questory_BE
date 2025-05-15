package com.ssafy.questory.dto.request.member;

import lombok.Builder;
import lombok.Getter;

@Getter
public class EmailVerifyRequestDto {
    private String email;
    private String code;

    @Builder
    private EmailVerifyRequestDto(String email, String code) {
        this.email = email;
        this.code = code;
    }
}
