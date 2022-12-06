package com.ssafy.letcipe.api.dto.user;

import com.ssafy.letcipe.domain.user.JobType;
import com.ssafy.letcipe.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResGetWriterDto {
    private Long id;
    private String nickname;
    private String profileImage;
    private JobType job;

    public ResGetWriterDto(User user){
        this.id = user.getId();
        this.nickname = user.getNickname();
        this.profileImage = user.getProfileImage();
        this.job = user.getJob();
    }
}
