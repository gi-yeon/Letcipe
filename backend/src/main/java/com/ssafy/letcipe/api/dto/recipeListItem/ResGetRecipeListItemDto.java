package com.ssafy.letcipe.api.dto.recipeListItem;

import com.ssafy.letcipe.api.dto.recipe.ResGetRecipeDto;
import com.ssafy.letcipe.domain.recipe.Recipe;
import com.ssafy.letcipe.domain.recipeListItem.RecipeListItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ResGetRecipeListItemDto {
    private ResGetRecipeDto recipe;
    private Integer amount;

    public ResGetRecipeListItemDto(RecipeListItem recipeListItem){
        this.recipe = ResGetRecipeDto.createDto(recipeListItem.getRecipe());
        this.amount = recipeListItem.getAmount();
    }
}
