package com.ssafy.letcipe.api.dto.board;

import com.ssafy.letcipe.api.dto.ResGetBoardCommentDto;
import com.ssafy.letcipe.domain.board.Board;
import com.ssafy.letcipe.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

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
