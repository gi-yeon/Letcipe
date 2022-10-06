package com.ssafy.letcipe.api.dto.user;

import com.ssafy.letcipe.api.dto.recipe.ResGetRecipeDto;
import com.ssafy.letcipe.domain.recipeListItem.RecipeListItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ResGetUserRecipeListItemDto {
    private ResGetUserRecipeInListDto recipe;
    private Integer amount;

    public ResGetUserRecipeListItemDto(RecipeListItem recipeListItem){
        this.recipe = new ResGetUserRecipeInListDto(recipeListItem.getRecipe());
        this.amount = recipeListItem.getAmount();
    }
}
