package com.ssafy.letcipe.api.dto.board;

import com.ssafy.letcipe.domain.board.Board;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResGetBoardDto {
    private Long id;
    private String nickname;
    private String title;
    private String content;
    private Long likes;
    private LocalDateTime regTime;

    public static ResGetBoardDto createDto(Board board) {
        return ResGetBoardDto.builder().id(board.getId())
                .nickname(board.getUser().getNickname())
                .title(board.getTitle())
                .content(board.getContent())
                .likes(board.getLikes())
                .regTime(board.getRegTime()).build();
    }

}
