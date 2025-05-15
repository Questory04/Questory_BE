package com.ssafy.questory.controller;

import com.ssafy.questory.dto.request.member.EmailVerifyRequestDto;
import com.ssafy.questory.dto.request.member.MemberEmailRequestDto;
import com.ssafy.questory.dto.request.member.MemberLoginRequestDto;
import com.ssafy.questory.dto.request.member.MemberRegistRequestDto;
import com.ssafy.questory.dto.response.member.MemberRegistResponseDto;
import com.ssafy.questory.dto.response.member.MemberTokenResponseDto;
import com.ssafy.questory.service.mail.MailSendService;
import com.ssafy.questory.service.MemberService;
import com.ssafy.questory.service.mail.PasswordResetService;
import com.ssafy.questory.service.mail.VerifyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController()
@RequestMapping("/members")
@RequiredArgsConstructor
@Tag(name = "회원 API", description = "회원가입, 로그인 등 회원 관련 API")
public class MemberController {
    private final MemberService userService;
    private final MailSendService mailSendService;
    private final PasswordResetService passwordResetService;
    private final VerifyService verifyService;

    @PostMapping("/regist")
    @Operation(summary = "회원가입", description = "회원가입을 처리합니다.")
    public ResponseEntity<MemberRegistResponseDto> regist(@RequestBody MemberRegistRequestDto memberRegistRequestDto) {
        MemberRegistResponseDto memberRegistResponseDto = userService.regist(memberRegistRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(memberRegistResponseDto);
    }

    @PostMapping("/login")
    @Operation(summary = "로그인", description = "로그인을 처리합니다.")
    public ResponseEntity<MemberTokenResponseDto> login(@RequestBody MemberLoginRequestDto memberLoginRequestDto) {
        MemberTokenResponseDto memberTokenResponseDto = userService.login(memberLoginRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(memberTokenResponseDto);
    }

    @PostMapping("/find-password")
    @Operation(summary = "비밀번호 재발급", description = "비밀번호 재발급을 처리합니다.")
    public ResponseEntity<Map<String, String>> findPassword(
            @RequestBody MemberEmailRequestDto memberEmailRequestDto) {
        mailSendService.sendEmail(passwordResetService.buildMail(memberEmailRequestDto.getEmail()));
        return ResponseEntity.ok().body(Map.of(
                "message", "임시 비밀번호를 이메일로 전송했습니다."
        ));
    }

    @PostMapping("/send-verify")
    @Operation(summary = "인증코드 발급 및 발송", description = "인증 코드 발급 및 발송을 처리합니다.")
    public ResponseEntity<Map<String, String>> sendVerifyEmail(@RequestBody MemberEmailRequestDto memberEmailRequestDto) {
        mailSendService.sendEmail(verifyService.buildMail(memberEmailRequestDto.getEmail()));
        return ResponseEntity.ok().body(Map.of(
                "message", "인증코드를 이메일로 전송했습니다."
        ));
    }

    @PostMapping("/verify-code")
    @Operation(summary = "인증코드 검증", description = "인증코드 검증을 처리합니다.")
    public ResponseEntity<Map<String, String>> verifyCode(@RequestBody EmailVerifyRequestDto emailVerifyRequestDto) {
        verifyService.checkVerifyCode(emailVerifyRequestDto);
        return ResponseEntity.ok().body(Map.of(
                "message", "인증이 완료되었습니다."
        ));
    }
}
