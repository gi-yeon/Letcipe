package com.ssafy.letcipe.api.dto.user;

import com.ssafy.letcipe.domain.user.GenderType;
import com.ssafy.letcipe.domain.user.JobType;
import com.ssafy.letcipe.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
public class ResGetUserDto {
    long id;
    String userId;
    String name;
    String nickname;
    String profileImage;
    String email;
    String phone;
    GenderType gender;
    JobType job;
    Integer family;
    LocalDate birth;

    public static ResGetUserDto createDto(User user) {
        return ResGetUserDto.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .profileImage(user.getProfileImage())
                .gender(user.getGender())
                .job(user.getJob())
                .family(user.getFamily())
                .birth(user.getBirth())
                .build();
    }

    public ResGetUserDto(User user) {
        this.id = user.getId();
        this.nickname = user.getNickname();
    }
}
