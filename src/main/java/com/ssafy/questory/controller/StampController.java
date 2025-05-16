package com.ssafy.questory.controller;

import com.ssafy.questory.dto.response.stamp.StampsResponseDto;
import com.ssafy.questory.service.StampService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stamp")
@RequiredArgsConstructor
public class StampController {
    private final StampService stampService;

    @GetMapping("")
    public ResponseEntity<List<StampsResponseDto>> findStamps(@RequestParam int page, @RequestParam int size){
        List<StampsResponseDto> stampsResponseDtoList = stampService.findStamps(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(stampsResponseDtoList);
    }

}
