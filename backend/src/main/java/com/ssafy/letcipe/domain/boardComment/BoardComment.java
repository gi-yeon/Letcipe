package com.ssafy.letcipe.domain.boardComment;

import com.ssafy.letcipe.domain.board.Board;
import com.ssafy.letcipe.domain.type.StatusType;
import com.ssafy.letcipe.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    private LocalDateTime regTime;

    @Column(name = "mod_time")
    private LocalDateTime modTime;

    @Column(name = "is_deleted", nullable = false)
    private StatusType statusType;


    @Builder
    public BoardComment(User user, Board board, String content, LocalDateTime regTime, LocalDateTime modTime, StatusType statusType){
        this.user = user;
        this.board = board;
        this.content = content;
        this.regTime = regTime;
        this.modTime = modTime;
        this.statusType = statusType;
    }

    public void putBoardComment(String content){
        this.content = content;
        this.modTime = LocalDateTime.now();
    }

    public void patchBoardComment() {
        this.statusType = StatusType.Y;
    }
}
