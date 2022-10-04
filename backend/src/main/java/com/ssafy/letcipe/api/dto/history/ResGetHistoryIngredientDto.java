package com.ssafy.letcipe.api.dto.history;

import com.ssafy.letcipe.api.dto.ingredient.ResGetIngredientDto;
import com.ssafy.letcipe.domain.historyIngredient.HistoryIngredient;
import com.ssafy.letcipe.domain.historyIngredient.PurchaseType;
import com.ssafy.letcipe.domain.ingredient.Ingredient;
import lombok.Getter;

@Getter
public class ResGetHistoryIngredientDto {

    private Long id;
    private String name;
    private String category;
    private String categoryName;
    private Double amount;
    private String measure;
    private Double gml;
    private PurchaseType isPurchased;

    public ResGetHistoryIngredientDto(HistoryIngredient historyIngredient, String categoryName) {
        this.id = historyIngredient.getId();
        this.name = historyIngredient.getIngredient().getName();
        this.amount = historyIngredient.getAmount();
        this.isPurchased = historyIngredient.getPurchaseType();
        this.category = historyIngredient.getIngredient().getCategory();
        this.categoryName = categoryName;
        this.measure = historyIngredient.getIngredient().getMeasure();
        this.gml = historyIngredient.getIngredient().getGml();

    }
}
