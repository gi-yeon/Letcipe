package com.ssafy.letcipe.api.dto;

import com.ssafy.letcipe.domain.boardComment.BoardComment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResGetBoardCommentDto {
    private Long id;
    private String nickname;
    private String content;
    private LocalDateTime regTime;
    private LocalDateTime modTime;

    public static ResGetBoardCommentDto createDto(BoardComment boardComment){
        return ResGetBoardCommentDto.builder()
                .id(boardComment.getId())
                .nickname(boardComment.getUser().getNickname())
                .content(boardComment.getContent())
                .regTime(boardComment.getRegTime())
                .modTime(boardComment.getModTime()).build();
    }

}
