package com.ssafy.questory.controller;

import com.ssafy.questory.domain.Member;
import com.ssafy.questory.dto.request.friend.FriendStatusRequestDto;
import com.ssafy.questory.dto.request.member.MemberEmailRequestDto;
import com.ssafy.questory.dto.response.member.auth.MemberInfoResponseDto;
import com.ssafy.questory.service.FriendService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/friends")
@RequiredArgsConstructor
public class FollowController {
    private final FriendService friendService;

    @GetMapping("")
    @Operation(summary = "친구 목록 조회", description = "나의 친구 목록을 조회합니다.")
    public ResponseEntity<List<MemberInfoResponseDto>> getFriendsInfo(
            @AuthenticationPrincipal(expression = "member") Member member) {
        return ResponseEntity.ok(friendService.getFriendsInfo(member));
    }

    @PostMapping("/request")
    @Operation(summary = "친구 요청", description = "친구를 요청합니다.")
    public ResponseEntity<Map<String, String>> request(
            @AuthenticationPrincipal(expression = "member") Member member,
            @RequestBody MemberEmailRequestDto memberEmailRequestDto) {
        friendService.request(member, memberEmailRequestDto);
        return ResponseEntity.ok().body(Map.of(
                "message", "친구 요청이 전송되었습니다."
        ));
    }

    @PatchMapping("/request")
    @Operation(summary = "친구 요청 수락/거절", description = "친구 요청을 수락 및 거절합니다.")
    public ResponseEntity<Map<String, String>> updateStatus(
            @AuthenticationPrincipal(expression = "member") Member member,
            @RequestBody FriendStatusRequestDto friendStatusRequestDto) {
        friendService.update(member, friendStatusRequestDto);
        return ResponseEntity.ok().body(Map.of(
                "message", "친구 요청이 업데이트 되었습니다."
        ));
    }
}
