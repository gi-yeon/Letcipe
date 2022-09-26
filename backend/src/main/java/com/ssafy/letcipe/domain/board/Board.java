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
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "likes")
    @ColumnDefault("0")
    private Long likes;

    @Column(name = "is_deleted", nullable = false)
    private StatusType statusType;

    @Column(name = "reg_time")
    private LocalDateTime regTime;

    @Builder
    public Board(User user, String title, String content, StatusType statusType, LocalDateTime regTime) {
        this.user = user;
        this.title = title;
        this.content = content;
        this.statusType = statusType;
        this.regTime = regTime;
    }

    public void putBoard(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void patchBoard() {
        this.statusType = StatusType.Y;
    }
}
