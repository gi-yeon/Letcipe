package com.ssafy.letcipe.domain.historyListItem;

import com.ssafy.letcipe.domain.history.History;
import com.ssafy.letcipe.domain.recipe.Recipe;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@IdClass(HistoryItemPK.class)
public class HistoryItem {

    @Id
    @ManyToOne(targetEntity = History.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "history_id", referencedColumnName = "id")
    private History history;

    @Id
    @ManyToOne(targetEntity = Recipe.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id", referencedColumnName = "id")
    private Recipe recipe;

    @Column(name = "amount", nullable = false)
    private Integer amount;
}
