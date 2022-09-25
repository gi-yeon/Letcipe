package com.ssafy.letcipe.api.dto.history;

import com.ssafy.letcipe.api.dto.recipe.ResGetRecipeDto;
import com.ssafy.letcipe.domain.historyItem.HistoryItem;
import lombok.Getter;

@Getter
public class ResGetHistoryItemDto {
    private ResGetRecipeDto recipe;
    private Integer amount;

    public ResGetHistoryItemDto(HistoryItem historyItem){
//        this.recipe = new ResGetRecipeDto(historyItem.getRecipe(),null);
        this.amount = historyItem.getAmount();
    }
}
