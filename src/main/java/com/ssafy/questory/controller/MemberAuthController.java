package com.ssafy.questory.controller;

import com.ssafy.questory.domain.Member;
import com.ssafy.questory.dto.request.member.MemberRegistRequestDto;
import com.ssafy.questory.dto.response.member.MemberRegistResponseDto;
import com.ssafy.questory.dto.response.member.auth.MemberInfoResponse;
import com.ssafy.questory.service.MemberAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/me")
@RequiredArgsConstructor
@Tag(name = "[인증] 회원 API", description = "내 정보 조회, 수정, 탈퇴 등 인증된 회원 관련 API")
public class MemberAuthController {
    private final MemberAuthService memberAuthService;

    @GetMapping("")
    @Operation(summary = "내 정보 조회", description = "내 정보를 조회합니다.")
    public ResponseEntity<MemberInfoResponse> getMyInfo(
            @AuthenticationPrincipal(expression = "member") Member member) {
        return ResponseEntity.ok(memberAuthService.getInfo(member));
    }

}
