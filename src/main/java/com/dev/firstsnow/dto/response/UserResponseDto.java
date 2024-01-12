package com.dev.firstsnow.dto.response;

import com.dev.firstsnow.domain.Letter;
import com.dev.firstsnow.domain.User;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;

@Builder
public record UserResponseDto(
        Long user_id,
        String nickname,
        String phone,
        String location,
        String snowman_num
) {
    public static UserResponseDto fromEntity(User user){
        UserResponseDto userResponseDto =
                UserResponseDto.builder()
                        .user_id(user.getId())
                        .nickname(user.getNickname())
                        .location(user.getLocation())
                        .phone(user.getPhone())
                        .snowman_num(user.getSnowman())
                        .build();
        return userResponseDto;
    }

    public static List<UserResponseDto> fromEntityList(List<User> users){
        List<UserResponseDto> dtoList = new ArrayList<>();

        for(User user : users){
            UserResponseDto userResponseDto =
                    UserResponseDto.builder()
                            .user_id(user.getId())
                            .nickname(user.getNickname())
                            .location(user.getLocation())
                            .build();

            dtoList.add(userResponseDto);
        }

        return dtoList;
    }
}
