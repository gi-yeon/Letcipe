package com.ssafy.letcipe.api.dto.cart;

import com.ssafy.letcipe.api.dto.ingredient.ResGetIngredientDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResGetCartIngredientDto {
    ResGetIngredientDto ingredient;
    Double amount;

}
