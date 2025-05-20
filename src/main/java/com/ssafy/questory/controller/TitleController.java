package com.ssafy.questory.controller;

import com.ssafy.questory.domain.Member;
import com.ssafy.questory.dto.request.title.TitleEarnRequestDto;
import com.ssafy.questory.dto.response.title.TitleInfoResponseDto;
import com.ssafy.questory.service.TitleService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/title")
@RequiredArgsConstructor
public class TitleController {
    private final TitleService titleService;

    @GetMapping()
    @Operation(summary = "칭호 조회", description = "획득한 칭호를 조회합니다.")
    public ResponseEntity<List<TitleInfoResponseDto>> getTitleInfos(
            @AuthenticationPrincipal(expression = "member") Member member) {
        return ResponseEntity.ok().body(titleService.getTitleInfo(member));
    }

    @PostMapping()
    @Operation(summary = "칭호 획득", description = "칭호를 획득합니다.")
    public ResponseEntity<Map<String, String>> earn(
            @AuthenticationPrincipal(expression = "member") Member member,
            @RequestBody TitleEarnRequestDto titleEarnRequestDto) {
        titleService.earn(member, titleEarnRequestDto);
        return ResponseEntity.ok().body(Map.of(
                "message", "칭호를 획득했습니다."
        ));
    }
}
