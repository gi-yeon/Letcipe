package com.ssafy.letcipe.domain.history;

import com.ssafy.letcipe.domain.historyIngredient.HistoryIngredient;
import com.ssafy.letcipe.domain.historyItem.HistoryItem;
import com.ssafy.letcipe.domain.type.StatusType;
import com.ssafy.letcipe.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.util.Lazy;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "reg_time")
    private LocalDateTime regTime;

    @Column(name = "process", nullable = false)
    private ProcessType process;

    @Column(name = "is_deleted", nullable = false)
    private StatusType isDeleted;

    @Column(name = "review")
    private String review;

    @OneToMany(targetEntity = HistoryItem.class, mappedBy = "history", fetch = FetchType.LAZY)
    private List<HistoryItem> historyItems = new ArrayList<>();

    @OneToMany(targetEntity = HistoryIngredient.class, mappedBy = "history", fetch = FetchType.LAZY)
    private List<HistoryIngredient> historyIngredients = new ArrayList<>();

    public void update(ProcessType process) {
        this.process = process;
    }

    public void delete() {
        this.isDeleted = StatusType.Y;
    }
}
