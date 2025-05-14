package com.ssafy.questory.service;

import com.ssafy.questory.domain.Member;
import com.ssafy.questory.dto.request.member.MemberFindPasswordRequestDto;
import com.ssafy.questory.dto.response.mail.MailResponseDto;
import com.ssafy.questory.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailSendService {
    private final MemberRepository memberRepository;
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    private final char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
            'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

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

        return MailResponseDto.builder()
                .email(email)
                .title(nickname + "님의 Questory 임시 비밀번호 안내입니다.")
                .content("안녕하세요.\nQuestory 임시 비밀번호 안내 드립니다.\n[" + nickname + "]님의 임시 비밀번호는 " + newPassword + "입니다.")
                .build();
    }

    public String getNewPassword() {
        StringBuilder newPassword = new StringBuilder();
        int idx = 0;
        for (int i = 0; i < 10; i++) {
            idx = (int) (charSet.length * Math.random());
            newPassword.append(idx);
        }
        return newPassword.toString();
    }
}
