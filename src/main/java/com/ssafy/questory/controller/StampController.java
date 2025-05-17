package com.ssafy.questory.controller;

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
@RequestMapping("/stamp")
@RequiredArgsConstructor
public class StampController {
    private final StampService stampService;

    @GetMapping("")
    public ResponseEntity<Map<String, Object>> findStamps(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size){
        List<StampsResponseDto> stampsResponseDtoList = stampService.findStamps(page, size);
        int totalItems = stampService.getTotalStamps();
        int totalPages = (int) Math.ceil((double) totalItems / size);

        Map<String, Object> response = new HashMap<>();
        response.put("stamps", stampsResponseDtoList);
        response.put("currentPage", page);
        response.put("totalItems", totalItems);
        response.put("totalPages", totalPages);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
