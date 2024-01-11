package com.dev.firstsnow.dto.response;

import com.dev.firstsnow.domain.Letter;
import com.dev.firstsnow.domain.User;
import lombok.Builder;

@Builder
public record CreateUserDto(
        Long user_id,
        String nickname,
        String phone,
        String location,
        String snowman_num
) {
    public static CreateUserDto fromEntity(User user){
        CreateUserDto createUserDto =
                CreateUserDto.builder()
                        .user_id(user.getId())
                        .nickname(user.getNickname())
                        .location(user.getLocation())
                        .phone(user.getPhone())
                        .snowman_num(user.getSnowman())
                        .build();
        return createUserDto;
    }
}
