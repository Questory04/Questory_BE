package com.ssafy.questory.dto.request.member;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberSearchRequestDto {
    private String email;
    private int page;
    private int size;

    @Builder
    public MemberSearchRequestDto(String email, int page, int size) {
        this.email = email;
        this.page = page;
        this.size = size;
    }
}
