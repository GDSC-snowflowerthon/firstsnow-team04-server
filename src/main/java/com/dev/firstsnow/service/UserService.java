package com.dev.firstsnow.service;

import com.dev.firstsnow.domain.User;
import com.dev.firstsnow.dto.request.UserRequestDto;
import com.dev.firstsnow.dto.response.UserResponseDto;
import com.dev.firstsnow.dto.response.IsDuplicateDto;
import com.dev.firstsnow.exception.CommonException;
import com.dev.firstsnow.exception.ErrorCode;
import com.dev.firstsnow.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    //회원가입
    public UserResponseDto createUser(UserRequestDto userRequestDto){

        boolean isDuplicate = userRepository.existsByNickname(userRequestDto.nickname());
        if (isDuplicate) {
            throw new CommonException(ErrorCode.DUPLICATED_SERIAL_ID);
        }

        User user = User.builder()
                .nickname(userRequestDto.nickname())
                .location(userRequestDto.location())
                .phone(userRequestDto.phone())
                .snowman(userRequestDto.snowman_num())
                .build();

        userRepository.save(user);

        return UserResponseDto.fromEntity(user);
    }

    //닉네임 중복검사
    public IsDuplicateDto isDuplicate(String nickname) {
        boolean isDuplicate = userRepository.existsByNickname(nickname);

        if (isDuplicate) {
            throw new CommonException(ErrorCode.DUPLICATED_SERIAL_ID);
        }

        return new IsDuplicateDto(false);
    }

    //로그인
    public UserResponseDto login(UserRequestDto userRequestDto) {
        String nickname = userRequestDto.nickname();

        User user = userRepository.findByNickname(nickname)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));

        return UserResponseDto.fromEntity(user);
    }

    //닉네임 변경
    public UserResponseDto changeNickname(Long userId, String nickname) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));

        user.updateNickname(nickname);

        userRepository.save(user);

        return UserResponseDto.fromEntity(user);
    }

    //위치 변경
    public UserResponseDto changeLocation(Long userId, String location) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));

        user.updateLocation(location);

        userRepository.save(user);

        return UserResponseDto.fromEntity(user);
    }

    //눈사람 변경
    public UserResponseDto changeSnowman(Long userId, String snowman) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));

        user.updateSnowman(snowman);

        userRepository.save(user);

        return UserResponseDto.fromEntity(user);
    }

}
