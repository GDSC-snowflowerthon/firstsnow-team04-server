package com.dev.firstsnow.dto.response;

import com.dev.firstsnow.domain.Letter;
import com.dev.firstsnow.domain.User;
import lombok.Builder;

@Builder
public record ReadLetterDto(
        String title,
        String content,
        String created_date,
        String sender_name
){
    public static ReadLetterDto fromEntity(Letter letter){
        ReadLetterDto readLetterDto =
                ReadLetterDto.builder()
                        .title(letter.getTitle())
                        .content(letter.getContent())
                        .created_date(letter.getCreatedDate().toString())
                        .sender_name(letter.getSender().getNickname())
                        .build();
        return readLetterDto;
    }
}
