package com.dev.firstsnow.dto.response;

import com.dev.firstsnow.domain.Letter;
import lombok.Builder;

@Builder
public record LetterResponseDto(
    Long letter_id,
    String title,
    String content,
    Long sender_id,
    Long recipient_id,
    String created_date
) {
    public static LetterResponseDto fromEntity(Letter letter){
        LetterResponseDto letterResponseDto =
                LetterResponseDto.builder()
                        .title(letter.getTitle())
                        .content(letter.getContent())
                        .created_date(letter.getCreatedDate().toString())
                        .sender_id(letter.getSender().getId())
                        .recipient_id(letter.getRecipient().getId())
                        .letter_id(letter.getId())
                        .build();
        return letterResponseDto;
    }
}
