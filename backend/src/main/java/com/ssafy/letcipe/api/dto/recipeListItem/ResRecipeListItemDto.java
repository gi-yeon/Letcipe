package com.ssafy.letcipe.api.dto.recipeListItem;

import com.ssafy.letcipe.domain.recipeListItem.RecipeListItem;
import lombok.Getter;

@Getter
public class ResRecipeListItemDto {
    private Long recipeId;
    private Integer amount;
    private String repImg;

    public ResRecipeListItemDto(RecipeListItem recipeListItem) {
        this.recipeId = recipeListItem.getRecipe().getId();
        this.amount = recipeListItem.getAmount();
        this.repImg = recipeListItem.getRecipe().getRepImg();
    }
}
