package com.ssafy.questory.dto.request.member;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberModifyRequestDto {
    private String nickname;
    private String title;
    private boolean mode;

    @Builder
    private MemberModifyRequestDto(String nickname, String title, boolean mode) {
        this.nickname = nickname;
        this.title = title;
        this.mode = mode;
    }
}
