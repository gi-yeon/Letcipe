package com.ssafy.letcipe.api.dto.user;


import com.ssafy.letcipe.domain.recipe.Recipe;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ResGetUserRecipeInListDto {
    long id;
    String title;
    String repImg;

    public ResGetUserRecipeInListDto(Recipe recipe) {
        this.id = recipe.getId();
        this.title = recipe.getTitle();
        this.repImg = recipe.getRepImg();
    }
}
