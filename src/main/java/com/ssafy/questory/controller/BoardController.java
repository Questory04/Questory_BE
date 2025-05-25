package com.ssafy.questory.controller;

import com.ssafy.questory.domain.Member;
import com.ssafy.questory.dto.request.posts.PostsCreateRequestDto;
import com.ssafy.questory.service.CommunityService;
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
@RequestMapping("/posts")
@RequiredArgsConstructor
public class BoardController {
    private final CommunityService communityService;

    @PostMapping("")
    @Operation(summary = "게시판 글 작성", description = "게시판에 글을 작성합니다.")
    public ResponseEntity<Map<String, String>> create(
            @AuthenticationPrincipal(expression = "member") Member member,
            @RequestBody PostsCreateRequestDto postsCreateRequestDto) {
        System.out.println("게시글 작성 요청 도착");
        communityService.create(member, postsCreateRequestDto);
        return ResponseEntity.ok().body(Map.of(
                "message", "글이 등록되었습니다."
        ));
    }
}
