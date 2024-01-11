package com.dev.firstsnow.controller;

import com.dev.firstsnow.dto.common.ResponseDto;
import com.dev.firstsnow.dto.response.ReadLetterDto;
import com.dev.firstsnow.service.LetterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/v1")
public class LetterController {
    private final LetterService letterService;

    @GetMapping // 편지 조회
    public ResponseDto<?> readLetter(@PathVariable Long letterId){
        return ResponseDto.ok(letterService.readLetter(letterId));
    }
}
