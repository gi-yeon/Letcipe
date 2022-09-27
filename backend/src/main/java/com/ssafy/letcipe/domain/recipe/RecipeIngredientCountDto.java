package com.ssafy.letcipe.domain.recipe;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RecipeIngredientCountDto {
    Recipe recipe;
    Long count;
}
