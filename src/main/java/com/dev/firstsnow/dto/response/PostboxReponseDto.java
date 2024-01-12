package com.dev.firstsnow.dto.response;

import com.dev.firstsnow.domain.Letter;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;

@Builder
public record PostboxReponseDto(
        Long letter_id,
        String sender,
        Boolean is_read
) {
    public static List<PostboxReponseDto> fromEntityList(List<Letter> letters){
        List<PostboxReponseDto> dtoList = new ArrayList<>();

        for(Letter letter : letters){
            PostboxReponseDto postboxReponseDto =
                    PostboxReponseDto.builder()
                            .letter_id(letter.getId())
                            .sender(letter.getSender().getNickname())
                            .is_read(letter.getIsRead())
                            .build();

            dtoList.add(postboxReponseDto);
        }

        return dtoList;
    }
}
