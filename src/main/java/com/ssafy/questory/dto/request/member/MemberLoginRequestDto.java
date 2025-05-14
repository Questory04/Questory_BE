package com.ssafy.questory.dto.request.member;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberLoginRequestDto {
    private String email;
    private String password;

    @Builder
    private MemberLoginRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
