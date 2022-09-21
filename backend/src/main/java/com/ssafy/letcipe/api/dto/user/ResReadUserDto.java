package com.ssafy.letcipe.api.dto.user;

import com.ssafy.letcipe.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ResReadUserDto {
    long id;
    String nickname;

    public static ResReadUserDto createDto(User user) {
        return ResReadUserDto.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .build();
    }
}
