package com.ssafy.letcipe.api.dto.user;

import com.ssafy.letcipe.domain.user.GenderType;
import com.ssafy.letcipe.domain.user.JobType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqPostUserDto {
    String name, userId, password, email, nickname, phone;
    int family;
    LocalDate birth;
    GenderType gender;
    JobType job;
}
