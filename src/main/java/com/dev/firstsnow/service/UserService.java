package com.dev.firstsnow.service;

import com.dev.firstsnow.domain.User;
import com.dev.firstsnow.dto.request.UserRequestDto;
import com.dev.firstsnow.dto.response.CreateUserDto;
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

    public CreateUserDto createUser(UserRequestDto userRequestDto){
        User user = User.builder()
                .nickname(userRequestDto.nickname())
                .location(userRequestDto.location())
                .phone(userRequestDto.phone())
                .snowman(userRequestDto.snowman_num())
                .build();

        userRepository.save(user);

        return CreateUserDto.fromEntity(user);
    }

    public IsDuplicateDto isDuplicate(String nickname) {
        boolean isDuplicate = userRepository.existsByNickname(nickname);

        if (isDuplicate) {
            throw new CommonException(ErrorCode.DUPLICATED_SERIAL_ID);
        }

        return new IsDuplicateDto(false);
    }
}
