package com.ssafy.letcipe.domain.historyListItem;

import com.ssafy.letcipe.domain.history.History;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NoArgsConstructor
@Getter
@IdClass(HistoryItem.class)
public class HistoryItem implements Serializable {

    @Id
    @ManyToOne(targetEntity = History.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "history_id", referencedColumnName = "id")
    private History history;

//    @Id
//    @ManyToOne(targetEntity = Recipe.class, fetch = FetchType.LAZY)
//    @JoinColumn(name = "recipe_id", referencedColumnName = "id")
//    private Recipe recipe;

    @Column(name = "amount", nullable = false)
    private Integer amount;
}
