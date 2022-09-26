package com.ssafy.letcipe.domain.comment;

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

public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "reg_time")
    private LocalDateTime regTime;

    @Column(name = "mod_time")
    private LocalDateTime modTime;

    @Column(name = "board_id", nullable = false)
    private Long boardId;

    @Column(name = "board_type", nullable = false)
    private BoardType boardType;

    @Column(name = "is_deleted", nullable = false)
    private StatusType statusType;

    @Builder
    public Comment(User user, String content, Long boardId, BoardType boardType, StatusType statusType) {
        this.user = user;
        this.content = content;
        this.regTime = LocalDateTime.now();
        this.modTime = LocalDateTime.now();
        this.boardId = boardId;
        this.boardType = boardType;
        this.statusType = statusType;
    }


    public void patchComment() {
        this.statusType = StatusType.Y;
    }

    public void putComment(String content) {
        this.content = content;
        this.modTime = LocalDateTime.now();
    }


}
