package com.ssafy.questory.dto.response.member;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberSearchResponseDto {
    private String email;
    private String nickname;
    private String profileImageUrl;

    @Builder
    private MemberSearchResponseDto(String email, String nickname, String profileImageUrl) {
        this.email = email;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
    }

    public static MemberSearchResponseDto from(String email, String nickname, String profileImageUrl) {
        return MemberSearchResponseDto.builder()
                .email(email)
                .nickname(nickname)
                .profileImageUrl(profileImageUrl)
                .build();
    }
}
