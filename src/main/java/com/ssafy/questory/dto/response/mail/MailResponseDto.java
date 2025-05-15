package com.ssafy.questory.dto.response.mail;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MailResponseDto {
    private String email;
    private String title;
    private String content;

    @Builder
    private MailResponseDto(String email, String title, String content) {
        this.email = email;
        this.title = title;
        this.content = content;
    }
}
