package com.ssafy.questory.dto.request.member;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberRegistRequestDto {
    private String email;
    private String password;
    private String nickname;

    @Builder
    private MemberRegistRequestDto(String email, String password, String nickname) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }
}
