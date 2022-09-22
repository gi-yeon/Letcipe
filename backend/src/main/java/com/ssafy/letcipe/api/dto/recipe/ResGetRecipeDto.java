package com.ssafy.letcipe.api.dto.recipe;

import com.ssafy.letcipe.api.dto.ingredient.ResGetIngredientDto;
import com.ssafy.letcipe.api.dto.recipeIngredient.ResGetRecipeIngredientDto;
import com.ssafy.letcipe.api.dto.recipeTag.ResGetRecipeTagDto;
import com.ssafy.letcipe.api.dto.user.ResGetUserDto;
import com.ssafy.letcipe.domain.recipe.Recipe;
import com.ssafy.letcipe.domain.recipeTag.RecipeTag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class ResGetRecipeDto {
    long id;
    ResGetUserDto user;
    String title;
    String content;
    int cookingTime;
    int serving;
    String category;
    String repImg;
    int recipeLike;
    int recipeBookmark;
    int recipeComment;
    List<ResGetRecipeIngredientDto> ingredients;
    List<ResGetRecipeTagDto> tags;

    public ResGetRecipeDto(Recipe recipe,List<ResGetRecipeIngredientDto> ingredients) {
        this.id = recipe.getId();
        this.user = ResGetUserDto.createDto(recipe.getUser());
        this.title = recipe.getTitle();
        this.serving = recipe.getServing();
        this.content = recipe.getContent();
        this.cookingTime = recipe.getCookingTime();
        this.repImg = recipe.getRepImg();
        this.category = recipe.getCategory();
        this.recipeLike = recipe.getLikes().size();
        this.recipeBookmark = recipe.getBookmarks().size();
        this.recipeComment = recipe.getComments().size();
        this.tags = new ArrayList<>();
        for (RecipeTag tag : recipe.getTags()) {
            tags.add(new ResGetRecipeTagDto(tag));
        }
        this.ingredients = ingredients;

    }
}
