package com.ssafy.letcipe.api.dto.board;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
//@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ResGetBoardListDto implements Serializable {
    private Long id;
    private String nickname;
    private String title;
    private Long likes;
    private LocalDateTime regTime;
}
