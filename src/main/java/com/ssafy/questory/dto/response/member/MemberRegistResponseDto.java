package com.ssafy.questory.dto.response.member;

import com.ssafy.questory.domain.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberRegistResponseDto {
    private String email;
    private String nickname;

    @Builder
    private MemberRegistResponseDto(String email, String nickname) {
        this.email = email;
        this.nickname = nickname;
    }

    public static MemberRegistResponseDto from(Member member) {
        return MemberRegistResponseDto.builder()
                .email(member.getEmail())
                .nickname(member.getNickname())
                .build();
    }
}
