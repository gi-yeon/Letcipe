package com.ssafy.letcipe.api.dto.comment;

import com.ssafy.letcipe.domain.comment.BoardType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReqPostCommentDto {
    private String content;
    private Long boardId;
    private BoardType boardType;
}
