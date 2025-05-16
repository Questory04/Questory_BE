package com.ssafy.questory.service.mail;

import com.ssafy.questory.common.exception.CustomException;
import com.ssafy.questory.common.util.RedisUtil;
import com.ssafy.questory.dto.request.member.EmailVerifyRequestDto;
import com.ssafy.questory.dto.response.mail.MailResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

import static com.ssafy.questory.common.exception.ErrorCode.VERIFICATION_CODE_MISMATCH;

@Service
@RequiredArgsConstructor
public class VerifyService implements MailContentBuilder {

    private final RedisUtil redisUtil;

    @Override
    public MailResponseDto buildMail(String email) {
        String verificationCode = generateVerificationCode();

        String title = "[Questory] 이메일 인증 코드 안내";
        String content = String.format("""
            안녕하세요, Questory입니다.

            요청하신 이메일 인증 코드를 안내드립니다.
            회원가입을 완료하려면 아래 인증 코드를 입력해주세요.

            인증 코드: %s

            해당 코드는 5분간 유효하며, 만료 후에는 다시 요청하셔야 합니다.

            감사합니다.
            """, verificationCode);

        redisUtil.setDataExpire(email, verificationCode, 60 * 5L);

        return MailResponseDto.builder()
                .email(email)
                .title(title)
                .content(content)
                .build();
    }

    private String generateVerificationCode() {
        int length = 6;
        String charSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder code = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int idx = random.nextInt(charSet.length());
            code.append(charSet.charAt(idx));
        }

        return code.toString();
    }

    public void checkVerifyCode(EmailVerifyRequestDto emailVerifyRequestDto) {
        String email = emailVerifyRequestDto.getEmail();
        String verifyCode = emailVerifyRequestDto.getCode();

        String verifyCodeByEmail = redisUtil.getData(email);
        if (!verifyCodeByEmail.equals(verifyCode)) {
            throw new CustomException(VERIFICATION_CODE_MISMATCH);
        }
    }
}
