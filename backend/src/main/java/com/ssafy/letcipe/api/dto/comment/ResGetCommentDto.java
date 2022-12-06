package com.ssafy.letcipe.api.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResGetCommentDto {
    private Long id;
    private String nickName;
    private String content;
    private LocalDateTime regTime;

}