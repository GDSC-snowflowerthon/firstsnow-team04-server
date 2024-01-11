package com.dev.firstsnow.service;

import com.dev.firstsnow.domain.Letter;
import com.dev.firstsnow.dto.request.LetterRequestDto;
import com.dev.firstsnow.dto.response.ReadLetterDto;
import com.dev.firstsnow.exception.CommonException;
import com.dev.firstsnow.exception.ErrorCode;
import com.dev.firstsnow.repository.LetterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LetterService {
    private final LetterRepository letterRepository;

    // 편지 작성
    public ReadLetterDto createLetter(LetterRequestDto letterRequestDto) {
        // 편지 불러와서 읽음으로 처리하고
        Letter letter = letterRepository.findById(letterId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_LETTER));

        letter.updateIsRead(true);

        letterRepository.save(letter);

        // 편지 내용 전송
        return ReadLetterDto.fromEntity(letter);
    }

    // 편지 조회
    public ReadLetterDto readLetter(Long letterId) {
        // 편지 불러와서 읽음으로 처리하고
        Letter letter = letterRepository.findById(letterId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_LETTER));

        letter.updateIsRead(true);

        letterRepository.save(letter);

        // 편지 내용 전송
        return ReadLetterDto.fromEntity(letter);
    }
}
