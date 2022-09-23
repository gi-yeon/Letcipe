package com.ssafy.letcipe.api.dto.history;

import com.ssafy.letcipe.domain.historyIngredient.HistoryIngredient;
import com.ssafy.letcipe.domain.historyIngredient.PurchaseType;

public class ResGetHistoryIngredientDto {
//    private ResGetIngredientDto ingredient;
    private Integer amount;
    private PurchaseType isPurchased;

    public ResGetHistoryIngredientDto(HistoryIngredient historyIngredient) {
//        this.ingredient = new ResGetIngredientDto(historyIngredient.getIngredient());
        this.amount = historyIngredient.getAmount();
        this.isPurchased = historyIngredient.getPurchaseType();
    }
}
