package com.ssafy.letcipe.domain.recipeIngredient;

import com.ssafy.letcipe.domain.ingredient.Ingredient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class IngredientDetailAmount {
    Ingredient ingredient;
    double amount;
    String tagName;
}
