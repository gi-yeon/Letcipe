package com.ssafy.letcipe.api.dto.board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReqPostBoardCommentDto {
    private Long boardId;              //content만 있어도 될듯..?
    private String content;
}
