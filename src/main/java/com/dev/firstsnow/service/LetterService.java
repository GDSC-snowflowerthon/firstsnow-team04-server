package com.dev.firstsnow.service;

import com.dev.firstsnow.domain.Letter;
import com.dev.firstsnow.domain.User;
import com.dev.firstsnow.dto.request.LetterRequestDto;
import com.dev.firstsnow.dto.response.LetterResponseDto;
import com.dev.firstsnow.dto.response.PostboxReponseDto;
import com.dev.firstsnow.dto.response.ReadLetterDto;
import com.dev.firstsnow.exception.CommonException;
import com.dev.firstsnow.exception.ErrorCode;
import com.dev.firstsnow.repository.LetterRepository;
import com.dev.firstsnow.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LetterService {
    private final LetterRepository letterRepository;
    private final UserRepository userRepository;

    // 편지 작성
    public LetterResponseDto createLetter(LetterRequestDto letterRequestDto, Long senderId) {
        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));
        User recipient = userRepository.findById(letterRequestDto.recipient_id())
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));

        Letter letter = Letter.builder()
                .title(letterRequestDto.title())
                .content(letterRequestDto.content())
                .isRead(false)
                .isSent(false)
                .recipient(recipient)
                .sender(sender)
                .build();

        letterRepository.save(letter);

        // 편지 내용 전송
        return LetterResponseDto.fromEntity(letter);
    }

    // 편지 조회
    @Transactional
    public ReadLetterDto readLetter(Long letterId) {
        // 편지 불러와서 읽음으로 처리하고
        Letter letter = letterRepository.findById(letterId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_LETTER));

        letter.updateIsRead(true);

        letterRepository.save(letter);

        // 편지 내용 전송
        return ReadLetterDto.fromEntity(letter);
    }

    // 우편함 조회
    @Transactional
    public List<PostboxReponseDto> readPost(Long userId) {
        // 해당 사용사가 받는이로 되어있고, isSent가 true 되어있는 편지들의 리스트를 전달

        // 유저가 없으면 에러 터트리기
        if(!userRepository.existsById(userId)){
            throw new CommonException(ErrorCode.NOT_FOUND_USER);
        }

        List<Letter> letters = letterRepository.findByRecipientIdAndIsSentTrue(userId);

        return PostboxReponseDto.fromEntityList(letters);
    }
}
