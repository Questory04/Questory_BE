package com.ssafy.questory.service;

import com.ssafy.questory.dto.response.mail.MailResponseDto;
import com.ssafy.questory.service.mail.MailSendService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class MailSendServiceTest {

    private JavaMailSender javaMailSender;
    private MailSendService mailSendService;

    private final String fromEmail = "test@questory.com";

    @BeforeEach
    void setUp() {
        javaMailSender = mock(JavaMailSender.class);
        mailSendService = new MailSendService(javaMailSender);
        ReflectionTestUtils.setField(mailSendService, "fromEmail", fromEmail);
    }

    @Test
    @DisplayName("메일이 정상적으로 전송되어야 한다.")
    void sendEmail_success() {
        // given
        MailResponseDto dto = MailResponseDto.builder()
                .email("receiver@example.com")
                .title("테스트 제목")
                .content("테스트 내용")
                .build();

        ArgumentCaptor<SimpleMailMessage> messageCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);

        // when
        mailSendService.sendEmail(dto);

        // then
        verify(javaMailSender, times(1)).send(messageCaptor.capture());

        SimpleMailMessage sentMessage = messageCaptor.getValue();
        assertThat(sentMessage.getTo()).containsExactly("receiver@example.com");
        assertThat(sentMessage.getFrom()).isEqualTo(fromEmail);
        assertThat(sentMessage.getText()).isEqualTo("테스트 내용");
    }
}
