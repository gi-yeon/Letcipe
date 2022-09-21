package com.ssafy.letcipe.domain.board;

import com.ssafy.letcipe.domain.boardComment.BoardComment;
import com.ssafy.letcipe.domain.type.StatusType;
import com.ssafy.letcipe.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@DynamicInsert
@Getter
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "BIGINT(20) UNSIGNED")
    private Long id;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "board")
    private List<BoardComment> boardCommentList;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "likes")
    @ColumnDefault("0")
    private Long likes;

    @Column(name = "is_deleted", nullable = false)
    private StatusType statusType;

    @CreatedDate //게시판이라는 그걸 만들때 reg_date 알아서 넣어줌. 현재 서버시간을 reg_date로 넣어줌.
    @Column(name = "reg_time")
    private LocalDateTime regTime;

    @Builder
    public Board(User user, String title, String content, StatusType statusType) {
        this.user = user;
        this.title = title;
        this.content = content;
        this.statusType = statusType;
    }

    public void putBoard(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void patchBoard() {
        this.statusType = StatusType.Y;
    }
}
