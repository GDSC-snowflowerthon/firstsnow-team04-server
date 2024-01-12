package com.dev.firstsnow.controller;

import com.dev.firstsnow.dto.common.ResponseDto;
import com.dev.firstsnow.dto.request.LetterRequestDto;
import com.dev.firstsnow.dto.response.ReadLetterDto;
import com.dev.firstsnow.service.LetterService;
import com.dev.firstsnow.util.TokenExtractor;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/v1/letter")
public class LetterController {
    private final LetterService letterService;
    private final TokenExtractor tokenExtractor;

    @PostMapping("/send-letter") // 편지 작성
    public ResponseDto<?> createLetter(@RequestBody LetterRequestDto letterRequestDto, HttpServletRequest request){
        Long userId = tokenExtractor.getId(request);

        return ResponseDto.ok(letterService.createLetter(letterRequestDto, userId));
    }

    @GetMapping("/read-letter") // 편지 조회
    public ResponseDto<?> readLetter(@RequestParam("letter_id") Long letterId, HttpServletRequest request){
        Long userId = tokenExtractor.getId(request);

        return ResponseDto.ok(letterService.readLetter(letterId));
    }

    @GetMapping("/read-post") // 우편함 조회
    public ResponseDto<?> readPost(HttpServletRequest request){
        Long userId = tokenExtractor.getId(request);
        return ResponseDto.ok(letterService.readPost(userId));
    }
}
