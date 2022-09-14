package com.ssafy.letcipe.domain.boardLike;

import com.ssafy.letcipe.domain.board.Board;
import com.ssafy.letcipe.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardLikePK implements Serializable {
    private Board board;
    private User user;
}

