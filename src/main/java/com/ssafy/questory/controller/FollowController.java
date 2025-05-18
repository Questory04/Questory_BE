package com.ssafy.questory.controller;

import com.ssafy.questory.domain.Member;
import com.ssafy.questory.dto.request.member.MemberEmailRequestDto;
import com.ssafy.questory.service.FriendService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/friends")
@RequiredArgsConstructor
public class FollowController {
    private final FriendService friendService;

    @PostMapping("")
    @Operation(summary = "친구 요청", description = "친구를 요청합니다.")
    public ResponseEntity<Map<String, String>> request(
            @AuthenticationPrincipal(expression = "member") Member member,
            @RequestBody MemberEmailRequestDto memberEmailRequestDto) {
        friendService.request(member, memberEmailRequestDto);
        return ResponseEntity.ok().body(Map.of(
                "message", "친구 요청이 전송되었습니다."
        ));
    }
}
