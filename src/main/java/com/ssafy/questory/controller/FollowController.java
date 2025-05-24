package com.ssafy.questory.controller;

import com.ssafy.questory.domain.Member;
import com.ssafy.questory.dto.request.friend.FriendStatusRequestDto;
import com.ssafy.questory.dto.request.member.MemberEmailRequestDto;
import com.ssafy.questory.dto.request.member.MemberSearchRequestDto;
import com.ssafy.questory.dto.response.friend.FollowResponseDto;
import com.ssafy.questory.dto.response.member.MemberSearchResponseDto;
import com.ssafy.questory.dto.response.member.auth.MemberInfoResponseDto;
import com.ssafy.questory.service.FriendService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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

    @GetMapping("/request")
    @Operation(summary = "친구 요청 목록 조회", description = "친구 요청 목록을 조회합니다.")
    public ResponseEntity<List<FollowResponseDto>> getFollowRequestInfo(
            @AuthenticationPrincipal(expression = "member") Member member) {
        return ResponseEntity.ok(friendService.getFollowRequestInfo(member));
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


    @DeleteMapping("/request")
    @Operation(summary = "친구 요청 취소", description = "친구 요청을 취소합니다.")
    public ResponseEntity<Map<String, String>> cancel(
            @AuthenticationPrincipal(expression = "member") Member member,
            @RequestBody MemberEmailRequestDto memberEmailRequestDto) {
        friendService.cancelRequest(member, memberEmailRequestDto);
        return ResponseEntity.ok().body(Map.of(
                "message", "친구 요청이 취소 되었습니다."
        ));
    }

    @GetMapping("/request/sent")
    @Operation(summary = "보낸 친구 요청 목록 조회", description = " 보낸 친구 요청 목록을 조회합니다.")
    public ResponseEntity<Page<FollowResponseDto>> getFollowRequests(
            @AuthenticationPrincipal(expression = "member") Member member,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(friendService.getFollowRequests(member, page, size));
    }

    @GetMapping("/search")
    @Operation(summary = "유저 검색", description = "이메일을 통해 유저를 검색합니다.")
    public ResponseEntity<Page<MemberSearchResponseDto>> search(
            @AuthenticationPrincipal(expression = "member") Member member,
            @ModelAttribute MemberSearchRequestDto memberSearchRequestDto) {
        return ResponseEntity.ok(friendService.search(member, memberSearchRequestDto));
    }
}
