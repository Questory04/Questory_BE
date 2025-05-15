package com.ssafy.questory.service;

import com.ssafy.questory.domain.Member;
import com.ssafy.questory.dto.request.member.MemberFindPasswordRequestDto;
import com.ssafy.questory.dto.response.mail.MailResponseDto;
import com.ssafy.questory.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
public class MailSendService {
    private final MemberRepository memberRepository;
    private final JavaMailSender mailSender;
    private final PasswordEncoder passwordEncoder;

    @Value("${spring.mail.username}")
    private String fromEmail;

    private final char[] charSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*".toCharArray();

    public void createAndSendEmail(MemberFindPasswordRequestDto memberFindPasswordRequestDto) {
        sendEmail(createEmail(memberFindPasswordRequestDto));
    }

    public void sendEmail(MailResponseDto mailResponseDto) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailResponseDto.getEmail());
        message.setFrom(fromEmail);
        message.setText(mailResponseDto.getContent());
        mailSender.send(message);
    }

    public MailResponseDto createEmail(MemberFindPasswordRequestDto memberFindPasswordRequestDto) {
        String email = memberFindPasswordRequestDto.getEmail();
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다: " + email));;
        String nickname = member.getNickname();

        String newPassword = getNewPassword();
        changePassword(member, newPassword);

        return MailResponseDto.builder()
                .email(email)
                .title(nickname + "님의 Questory 임시 비밀번호 안내입니다.")
                .content("안녕하세요.\nQuestory 임시 비밀번호 안내 드립니다.\n[" + nickname + "]님의 임시 비밀번호는 " + newPassword + "입니다.")
                .build();
    }

    public String getNewPassword() {
        SecureRandom random = new SecureRandom();
        StringBuilder newPassword = new StringBuilder();

        for (int i = 0; i < 12; i++) {
            int idx = random.nextInt(charSet.length);
            newPassword.append(charSet[idx]);
        }

        return newPassword.toString();
    }

    private void changePassword(Member member, String newPassword) {
        String encodedPassword = passwordEncoder.encode(newPassword);
        memberRepository.changePassword(member, encodedPassword);
    }
}
