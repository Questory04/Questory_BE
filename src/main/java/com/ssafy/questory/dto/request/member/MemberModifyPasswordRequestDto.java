package com.ssafy.questory.dto.request.member;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberModifyPasswordRequestDto {
    private String password;

    @Builder
    private MemberModifyPasswordRequestDto(String password) {
        this.password = password;
    }
}
