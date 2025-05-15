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
public class MemberController {
    private final MemberService userService;
    private final MailSendService mailSendService;
    private final PasswordResetService passwordResetService;
    private final VerifyService verifyService;

    @PostMapping("/regist")
    public ResponseEntity<MemberRegistResponseDto> regist(@RequestBody MemberRegistRequestDto memberRegistRequestDto) {
        MemberRegistResponseDto memberRegistResponseDto = userService.regist(memberRegistRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(memberRegistResponseDto);
    }

    @PostMapping("/login")
    public ResponseEntity<MemberTokenResponseDto> login(@RequestBody MemberLoginRequestDto memberLoginRequestDto) {
        MemberTokenResponseDto memberTokenResponseDto = userService.login(memberLoginRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(memberTokenResponseDto);
    }

    @PostMapping("/find-password")
    public ResponseEntity<Map<String, String>> findPassword(
            @RequestBody MemberEmailRequestDto memberEmailRequestDto) {
        mailSendService.sendEmail(passwordResetService.buildMail(memberEmailRequestDto.getEmail()));
        return ResponseEntity.ok().body(Map.of(
                "message", "임시 비밀번호를 이메일로 전송했습니다."
        ));
    }

    @PostMapping("send-verify")
    public ResponseEntity<Map<String, String>> sendVerifyEmail(@RequestBody EmailVerifyRequestDto emailVerifyRequestDto) {
        mailSendService.sendEmail(verifyService.buildMail(emailVerifyRequestDto.getEmail()));
        return ResponseEntity.ok().body(Map.of(
                "message", "인증코드를 이메일로 전송했습니다."
        ));
    }
}
