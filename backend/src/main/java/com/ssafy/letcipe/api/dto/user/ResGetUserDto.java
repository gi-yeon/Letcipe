package com.ssafy.letcipe.api.dto.user;

import com.ssafy.letcipe.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ResGetUserDto {
    long id;
    String nickname;

    public static ResGetUserDto createDto(User user) {
        return ResGetUserDto.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .build();
    }
}
