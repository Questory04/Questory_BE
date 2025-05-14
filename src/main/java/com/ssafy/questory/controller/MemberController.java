package com.ssafy.questory.controller;

import com.ssafy.questory.dto.request.member.MemberLoginRequestDto;
import com.ssafy.questory.dto.request.member.MemberRegistRequestDto;
import com.ssafy.questory.dto.response.member.MemberRegistResponseDto;
import com.ssafy.questory.dto.response.member.MemberTokenResponseDto;
import com.ssafy.questory.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService userService;

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
}
