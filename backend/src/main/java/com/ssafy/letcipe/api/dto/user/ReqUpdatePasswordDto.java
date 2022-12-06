package com.ssafy.letcipe.api.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReqUpdatePasswordDto {
    String password, newPassword;
}
