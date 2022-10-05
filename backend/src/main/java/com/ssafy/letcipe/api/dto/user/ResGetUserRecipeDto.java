package com.ssafy.letcipe.api.dto.user;

import com.ssafy.letcipe.domain.recipe.Recipe;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ResGetUserRecipeDto {
    long id;
    String title;
    String content;
    int serving;
    String category;
    String repImg;
    int recipeLike;
    int recipeBookmark;

    public ResGetUserRecipeDto(Recipe recipe){
        this.id = recipe.getId();
        this.title = recipe.getTitle();
        this.content = recipe.getContent();
        this.serving = recipe.getServing();
        this.category = recipe.getCategory();
        this.repImg = recipe.getRepImg();
        this.recipeLike = recipe.getLikes().size();
        this.recipeBookmark = recipe.getBookmarks().size();
    }
}