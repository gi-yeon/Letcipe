package com.ssafy.letcipe.api.dto.recipeIngredient;

import com.ssafy.letcipe.api.dto.ingredient.ResGetIngredientDto;
import lombok.Data;

@Data
public class ResGetRecipeIngredientDto {
    ResGetIngredientDto ingredient;
    double amount;
    public ResGetRecipeIngredientDto(ResGetIngredientDto ingredient, double amount) {
        this.ingredient = ingredient;
        this.amount = amount;
    }
}
