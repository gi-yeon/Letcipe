package com.ssafy.letcipe.api.dto.recipeStep;

import com.ssafy.letcipe.domain.recipeStep.RecipeStep;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResGetRecipeStepDto {
    int step;
    String content;
    String img;

    public ResGetRecipeStepDto(RecipeStep step) {
        this.step = step.getStep();
        this.content = step.getContent();
        this.img = step.getImg();
    }
}
