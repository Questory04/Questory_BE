package com.ssafy.questory.dto.response.member;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberTokenResponseDto {
    private String email;
    private String accessToken;

    @Builder
    private MemberTokenResponseDto(String email, String accessToken) {
        this.email = email;
        this.accessToken = accessToken;
    }

    public static MemberTokenResponseDto from(String email, String accessToken) {
        return MemberTokenResponseDto.builder()
                .email(email)
                .accessToken(accessToken)
                .build();
    }
}
