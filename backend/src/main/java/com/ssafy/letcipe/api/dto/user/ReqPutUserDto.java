package com.ssafy.letcipe.api.dto.user;

import com.ssafy.letcipe.domain.user.JobType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReqPutUserDto {
    String email, nickname;
    JobType job;
    int family;
}
