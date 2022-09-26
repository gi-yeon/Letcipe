package com.ssafy.letcipe.api.dto.recipeTag;

import com.ssafy.letcipe.domain.recipeTag.RecipeTag;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResGetRecipeTagDto {
    String name;

    public ResGetRecipeTagDto(RecipeTag tag) {
        this.name = tag.getTag().getName();
    }
}
