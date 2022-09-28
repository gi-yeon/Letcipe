package com.ssafy.letcipe.api.dto.history;

import com.ssafy.letcipe.api.dto.ingredient.ResGetIngredientDto;
import com.ssafy.letcipe.domain.historyIngredient.HistoryIngredient;
import com.ssafy.letcipe.domain.historyIngredient.PurchaseType;
import com.ssafy.letcipe.domain.ingredient.Ingredient;

public class ResGetHistoryIngredientDto {
    private String name;
    private Double amount;
    private PurchaseType isPurchased;

    public ResGetHistoryIngredientDto(HistoryIngredient historyIngredient) {
        this.name = historyIngredient.getIngredient().getName();
        this.amount = historyIngredient.getAmount();
        this.isPurchased = historyIngredient.getPurchaseType();
    }
}
