package com.ssafy.letcipe.api.dto.user;

import com.ssafy.letcipe.domain.user.GenderType;
import com.ssafy.letcipe.domain.user.JobType;
import com.ssafy.letcipe.domain.user.User;
import com.ssafy.letcipe.domain.user.UserType;
import lombok.Builder;
import lombok.Data;

@Data
public class ResGetUserDto {
    String name, nickname;
    GenderType gender;
    JobType job;
    UserType userType;

    @Builder
    public ResGetUserDto(User user) {
        this.name = user.getName();
        this.nickname = user.getNickname();
        this.gender = user.getGender();
        this.job = user.getJob();
        this.userType = user.getUserType();
    }
}
