package com.ssafy.questory.controller;

import com.ssafy.questory.dto.response.stamp.StampsResponseDto;
import com.ssafy.questory.service.StampService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stamp")
@RequiredArgsConstructor
public class StampController {
    private final StampService stampService;

    @GetMapping("")
    public ResponseEntity<StampsResponseDto> findStamps(@RequestParam int page, @RequestParam int size){
        StampsResponseDto stampsResponseDto = stampService.findStamps(page, size);
        System.out.println(stampsResponseDto.toString());
        return ResponseEntity.status(HttpStatus.OK).body(stampsResponseDto);
    }

}
