package com.ssafy.letcipe.domain.historyIngredient;

import com.ssafy.letcipe.domain.history.History;
import com.ssafy.letcipe.domain.ingredient.Ingredient;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "history_id", "ingredient_id" }) })
public class HistoryIngredient {

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

    @Column(name = "is_purchased", nullable = false)
    private PurchaseType purchaseType;

    public void updateHistoryIngredient(PurchaseType purchaseType){
        this.purchaseType = purchaseType;
    }
}
