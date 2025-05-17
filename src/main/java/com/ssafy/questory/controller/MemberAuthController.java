package com.ssafy.questory.controller;

import com.ssafy.questory.domain.Member;
import com.ssafy.questory.dto.request.member.MemberModifyPasswordRequestDto;
import com.ssafy.questory.dto.request.member.MemberModifyRequestDto;
import com.ssafy.questory.dto.response.member.auth.MemberInfoResponseDto;
import com.ssafy.questory.service.MemberAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/me")
@RequiredArgsConstructor
@Tag(name = "[인증] 회원 API", description = "내 정보 조회, 수정, 탈퇴 등 인증된 회원 관련 API")
public class MemberAuthController {
    private final MemberAuthService memberAuthService;

    @GetMapping("")
    @Operation(summary = "내 정보 조회", description = "내 정보를 조회합니다.")
    public ResponseEntity<MemberInfoResponseDto> getMyInfo(
            @AuthenticationPrincipal(expression = "member") Member member) {
        return ResponseEntity.ok(memberAuthService.getInfo(member));
    }

    @PatchMapping("/profile")
    @Operation(summary = "내 정보 수정 - nickname, title, mode",
            description = "nickname, title, mode에 대한 내 정보를 수정합니다.")
    public ResponseEntity<MemberInfoResponseDto> modify(
            @AuthenticationPrincipal(expression = "member") Member member,
            @RequestBody MemberModifyRequestDto memberModifyRequestDto) {
        return ResponseEntity.ok(memberAuthService.modify(member, memberModifyRequestDto));
    }

    @PatchMapping("/password")
    @Operation(summary = "내 정보 수정 - 비밀번호", description = "비밀번호를 수정합니다.")
    public ResponseEntity<MemberInfoResponseDto> modifyPassword(
            @AuthenticationPrincipal(expression = "member") Member member,
            @RequestBody MemberModifyPasswordRequestDto memberModifyPasswordRequestDto) {
        return ResponseEntity.ok(memberAuthService.modifyPassword(member, memberModifyPasswordRequestDto));
    }

    @PatchMapping("/withdraw")
    @Operation(summary = "회원탈퇴", description = "회원을 탈퇴합니다.")
    public ResponseEntity<Map<String, String>> withdraw(
            @AuthenticationPrincipal(expression = "member") Member member) {
        System.out.println("member = " + member);
        memberAuthService.withdraw(member);
        return ResponseEntity.ok().body(Map.of(
                "message", "탈퇴하였습니다."
        ));
    }
}
