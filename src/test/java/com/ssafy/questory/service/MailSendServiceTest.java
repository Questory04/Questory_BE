package com.ssafy.questory.service;

import com.ssafy.questory.domain.Member;
import com.ssafy.questory.dto.request.member.MemberFindPasswordRequestDto;
import com.ssafy.questory.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

class MailSendServiceTest {

    private MemberRepository memberRepository;
    private JavaMailSender mailSender;
    private PasswordEncoder passwordEncoder;

    private MailSendService mailSendService;

    @BeforeEach
    void setUp() {
        memberRepository = mock(MemberRepository.class);
        mailSender = mock(JavaMailSender.class);
        passwordEncoder = mock(PasswordEncoder.class);
        mailSendService = new MailSendService(memberRepository, mailSender, passwordEncoder);

        // 필드 주입 값 설정
        // reflection을 통한 값 설정 (실제로는 @Value가 주입되지만 테스트에선 직접 설정)
        try {
            var field = MailSendService.class.getDeclaredField("fromEmail");
            field.setAccessible(true);
            field.set(mailSendService, "noreply@questory.com");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void createAndSendEmail_정상동작() {
        // given
        String email = "test@questory.com";
        String nickname = "테스터";
        Member member = Member.builder()
                .email(email)
                .password("oldPassword")
                .nickname(nickname)
                .build();

        when(memberRepository.findByEmail(email)).thenReturn(Optional.of(member));
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        MemberFindPasswordRequestDto requestDto = MemberFindPasswordRequestDto.builder()
                .email(email)
                .build();

        // when
        mailSendService.createAndSendEmail(requestDto);

        // then
        verify(memberRepository).changePassword(eq(member), anyString());
        verify(mailSender).send(any(SimpleMailMessage.class));
    }

    @Test
    void getNewPassword_길이와문자검사() {
        // when
        String pw = mailSendService.getNewPassword();

        // then
        assertThat(pw).hasSize(12);
        assertThat(pw).matches("[A-Za-z0-9!@#$%^&*]+");
    }

    @Test
    void createAndSendEmail_없는이메일이면_예외발생() {
        // given
        String email = "none@questory.com";
        when(memberRepository.findByEmail(email)).thenReturn(Optional.empty());

        MemberFindPasswordRequestDto requestDto = MemberFindPasswordRequestDto.builder()
                .email(email)
                .build();

        // when & then
        assertThatThrownBy(() -> mailSendService.createAndSendEmail(requestDto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("존재하지 않는 이메일");
    }

}
