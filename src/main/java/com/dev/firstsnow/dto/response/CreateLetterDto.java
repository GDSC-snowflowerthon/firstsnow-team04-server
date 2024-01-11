package com.dev.firstsnow.dto.response;

import com.dev.firstsnow.domain.Letter;
import lombok.Builder;

@Builder
public record CreateLetterDto(
    Long letter_id,
    String title,
    String content,
    Long sender_id,
    Long recipient_id,
    String created_date
) {
    public static CreateLetterDto fromEntity(Letter letter){
        CreateLetterDto createLetterDto =
                CreateLetterDto.builder()
                        .title(letter.getTitle())
                        .content(letter.getContent())
                        .created_date(letter.getCreatedDate().toString())
                        .sender_id(letter.getSender().getId())
                        .recipient_id(letter.getRecipient().getId())
                        .letter_id(letter.getId())
                        .build();
        return createLetterDto;
    }
}
