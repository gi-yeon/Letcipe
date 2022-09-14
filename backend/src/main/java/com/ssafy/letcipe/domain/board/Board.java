package com.ssafy.letcipe.domain.board;

import com.ssafy.letcipe.domain.type.StatusType;
import com.ssafy.letcipe.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.Date;

@Entity
@NoArgsConstructor
@Getter
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
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
    @Temporal(TemporalType.TIMESTAMP)
    private Date regTime;

}
