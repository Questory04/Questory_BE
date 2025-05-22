package com.ssafy.questory.controller;

import com.ssafy.questory.dto.response.attraction.AttractionResponseDto;
import com.ssafy.questory.service.AttractionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/attractions")
@RequiredArgsConstructor
@Tag(name = "관광지 API", description = "관광지 관련 API")
public class AttractionController {

    private final AttractionService attractionService;

    @GetMapping("/search-attractions")
    @Operation(summary = "관광지 검색", description = "퀘스트 등록 시 관광지를 검색합니다.")
    public ResponseEntity<Map<String, Object>> searchAttractionByTitle(@RequestParam String searchKeyword){
        List<AttractionResponseDto> attractionsResponseDtoList = attractionService.searchAttractionByTitle(searchKeyword);

        Map<String, Object> response = new HashMap<>();
        response.put("attractions", attractionsResponseDtoList);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
