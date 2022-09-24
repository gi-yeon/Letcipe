package com.ssafy.letcipe.api.dto;

import com.ssafy.letcipe.domain.board.Board;
import com.ssafy.letcipe.domain.boardComment.BoardComment;
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
    private List<ResGetBoardCommentDto> comments;

    public static ResGetBoardDto createDto(Board board, User user, List<ResGetBoardCommentDto> comments) {
        return ResGetBoardDto.builder().id(board.getId())
                .nickname(user.getNickname())
                .title(board.getTitle())
                .content(board.getContent())
                .likes(board.getLikes())
                .regTime(board.getRegTime()).comments(comments).build();
    }

}
