package com.ssafy.letcipe.api.dto.user;

import com.ssafy.letcipe.domain.comment.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ResGetUserCommentDto {
    private Long id;
    private String content;
    private LocalDateTime regTime;
    private Long boardId;

    public ResGetUserCommentDto(Comment comment){
        this.id = comment.getId();
        this.content = comment.getContent();
        this.regTime = comment.getRegTime();
        this.boardId = comment.getBoardId();
    }
}
