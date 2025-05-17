package com.ssafy.questory.controller;

import com.ssafy.questory.config.jwt.JwtService;
import com.ssafy.questory.dto.response.stamp.StampsResponseDto;
import com.ssafy.questory.service.StampService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/stamps")
@RequiredArgsConstructor
public class StampController {
    private final StampService stampService;
    private final JwtService jwtService;

    @GetMapping("")
    public ResponseEntity<Map<String, Object>> findStamps(@RequestParam(defaultValue = "1") int page,
                                                          @RequestParam(defaultValue = "5") int size,
                                                          @RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.substring(7);
        String memberEmail = jwtService.extractUsername(token);

        List<StampsResponseDto> stampsResponseDtoList = stampService.findStamps(memberEmail, page, size);
        int totalItems = stampService.getTotalStamps(memberEmail);
        int totalPages = (int) Math.ceil((double) totalItems / size);

        Map<String, Object> pagination = new HashMap<>();
        pagination.put("currentPage", page);
        pagination.put("totalItems", totalItems);
        pagination.put("totalPages", totalPages);
        pagination.put("pageSize", size);

        Map<String, Object> response = new HashMap<>();
        response.put("stamps", stampsResponseDtoList);
        response.put("pagination", pagination);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}