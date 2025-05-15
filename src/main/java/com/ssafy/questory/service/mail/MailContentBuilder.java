package com.ssafy.questory.service.mail;

import com.ssafy.questory.dto.response.mail.MailResponseDto;

public interface MailContentBuilder {
    MailResponseDto buildMail(String email);
}
