package com.ssafy.letcipe.api.dto.user;

import com.ssafy.letcipe.domain.user.GenderType;
import com.ssafy.letcipe.domain.user.JobType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReqPutUserDto {
    String name, userId, email, nickname;
    int family;
    String birth;
    GenderType gender;
    JobType job;
    MultipartFile profileImg;
}
