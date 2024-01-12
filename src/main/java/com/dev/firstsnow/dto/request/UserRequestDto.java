package com.dev.firstsnow.dto.request;

public record UserRequestDto(
        String nickname,
        String phone,
        String location,
        String snowman_num
) {
}
