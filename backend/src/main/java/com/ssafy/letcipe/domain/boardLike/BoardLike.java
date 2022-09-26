package com.ssafy.letcipe.domain.boardLike;

import com.ssafy.letcipe.domain.board.Board;
import com.ssafy.letcipe.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class BoardLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne(targetEntity = Board.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", referencedColumnName = "id")
    private Board board;

}
