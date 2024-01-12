package com.dev.firstsnow.service;

import com.dev.firstsnow.domain.User;
import com.dev.firstsnow.dto.request.UserRequestDto;
import com.dev.firstsnow.dto.response.UserResponseDto;
import com.dev.firstsnow.dto.response.IsDuplicateDto;
import com.dev.firstsnow.exception.CommonException;
import com.dev.firstsnow.exception.ErrorCode;
import com.dev.firstsnow.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

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

    //닉네임 검색
    public List<UserResponseDto> searchUsers(String keyword,
                                             Integer pageIndex, Integer pageSize){
        // Pageable 객체를 생성하여 페이징 처리
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        Page<User> userPage;

        //keyword가 null 혹은 공백인 경우
        if (!StringUtils.hasText(keyword)) {
            userPage = userRepository.findAll(pageable);
        } else {
            // DiaryRepository를 사용하여 title과 content를 기반으로 검색하고 페이징된 결과를 가져옴
            userPage = userRepository.findByNicknameContaining(keyword, pageable);
        }

        // Page<Diary>에서 DiaryDetailDto 리스트로 변환
        List<UserResponseDto> userResponseDtos = userPage.getContent().stream()
                .map(UserResponseDto::fromEntity)
                .collect(Collectors.toList());

        return userResponseDtos;
    }
}
