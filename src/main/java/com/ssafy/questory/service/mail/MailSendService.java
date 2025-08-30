package com.ssafy.questory.service.mail;

import com.ssafy.questory.dto.response.mail.MailResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailSendService {
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Async("emailExecutor")
    public void sendEmail(MailResponseDto mailResponseDto) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailResponseDto.getEmail());
        message.setFrom(fromEmail);
        message.setText(mailResponseDto.getContent());
        mailSender.send(message);
    }
}
