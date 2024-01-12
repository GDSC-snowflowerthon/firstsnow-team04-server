package com.dev.firstsnow.dto.request;

public record LetterRequestDto(
    Long recipient_id,
    String title,
    String content
) {
}
