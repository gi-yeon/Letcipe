package com.ssafy.letcipe.api.dto.cart;

import com.ssafy.letcipe.api.dto.recipe.ResGetRecipeDto;
import com.ssafy.letcipe.domain.cart.Cart;
import com.ssafy.letcipe.domain.recipe.Recipe;
import com.ssafy.letcipe.domain.recipeListItem.RecipeListItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ResGetCartItemDto {
    private ResGetRecipeDto recipe;
    private Integer amount;

    public ResGetCartItemDto(Cart cart){
        this.recipe = ResGetRecipeDto.createDto(cart.getRecipe());
        this.amount = cart.getAmount();
    }
}
