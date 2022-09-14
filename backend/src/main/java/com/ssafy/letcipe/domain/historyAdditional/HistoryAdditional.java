package com.ssafy.letcipe.domain.historyAdditional;

import com.ssafy.letcipe.domain.history.History;
import com.ssafy.letcipe.domain.ingredient.Ingredient;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class HistoryAdditional {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(targetEntity = History.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "history_id", referencedColumnName = "id")
    private History history;

    @ManyToOne(targetEntity = Ingredient.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_id", referencedColumnName = "id")
    private Ingredient ingredient;

    @Column(name = "amount", nullable = false)
    private int amount;
}
