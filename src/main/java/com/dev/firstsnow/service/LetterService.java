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
import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
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

    @Transactional
    public void updateLettersSentStatusForLocation(String location) {
        List<User> users = userRepository.findByLocation(location);
        for (User user : users) {

            List<Letter> letters = letterRepository.findByRecipient(user);
            for (Letter letter : letters) {
                if (!letter.getIsSent()) {
                    String phone = user.getPhone().replace("-", "");
                    String url = "http://50cms.store/mailboxpage/" + user.getId();
                    testSend(phone, url);
                    log.info(phone);
                    letter.updateIsSent(true);
                    letterRepository.save(letter);
                }
            }
        }
    }

    public void testSend(String phone, String url) {
        DefaultMessageService messageService =  NurigoApp.INSTANCE.initialize("NCSEZASHIWVTM9MN", "ULVR13M5QUJA995PAMUI0EYTEWP9MPYD", "https://api.coolsms.co.kr");
// Message 패키지가 중복될 경우 net.nurigo.sdk.message.model.Message로 치환하여 주세요
        Message message = new Message();
        message.setFrom("01024331103");
        message.setTo(phone);
        message.setText("따뜻한 마음이 담긴 편지 하나가 도착했어요!!!~~\n\n해커톤 기간동안 고생많으셨습니다" + url);

        try {
            // send 메소드로 ArrayList<Message> 객체를 넣어도 동작합니다!
            messageService.send(message);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }
}
