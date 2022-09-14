package com.ssafy.letcipe.domain.boardComment;

import com.ssafy.letcipe.domain.board.Board;
import com.ssafy.letcipe.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@NoArgsConstructor
@Getter
public class BoardComment {

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

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "reg_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date regTime;

    @Column(name = "mod_time")
    private Date modTime;

}
