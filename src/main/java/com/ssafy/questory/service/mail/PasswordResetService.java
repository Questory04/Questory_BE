package com.ssafy.questory.service.mail;

import com.ssafy.questory.domain.Member;
import com.ssafy.questory.dto.request.member.MemberEmailRequestDto;
import com.ssafy.questory.dto.response.mail.MailResponseDto;
import com.ssafy.questory.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
public class PasswordResetService implements MailContentBuilder {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    private final char[] charSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*".toCharArray();

    @Override
    public MailResponseDto buildMail(MemberEmailRequestDto memberEmailRequestDto) {
        String email = memberEmailRequestDto.getEmail();
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다: " + email));

        String newPassword = getNewPassword();
        changePassword(member, newPassword);

        String title = "[Questory] 임시 비밀번호 안내드립니다.";
        String content = String.format("""
                안녕하세요, Questory입니다.

                회원님의 요청에 따라 임시 비밀번호를 발급해드렸습니다.
                아래의 임시 비밀번호로 로그인하신 후, 반드시 비밀번호를 변경해 주세요.

                임시 비밀번호: %s

                해당 비밀번호는 보안을 위해 1회성으로 사용되며,
                타인에게 노출되지 않도록 주의해주시기 바랍니다.

                감사합니다.
                """, newPassword);

        return MailResponseDto.builder()
                .email(email)
                .title(title)
                .content(content)
                .build();
    }

    private String getNewPassword() {
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
