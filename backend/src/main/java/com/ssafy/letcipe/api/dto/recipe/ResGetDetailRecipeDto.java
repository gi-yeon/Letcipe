package com.ssafy.letcipe.api.dto.recipe;

import com.ssafy.letcipe.api.dto.ingredient.ResGetIngredientDto;
import com.ssafy.letcipe.api.dto.recipeComment.ResGetRecipeCommentDto;
import com.ssafy.letcipe.api.dto.recipeIngredient.ResGetRecipeIngredientDto;
import com.ssafy.letcipe.api.dto.recipeStep.ResGetRecipeStepDto;
import com.ssafy.letcipe.api.dto.recipeTag.ResGetRecipeTagDto;
import com.ssafy.letcipe.api.dto.user.ResGetUserDto;
import com.ssafy.letcipe.domain.recipe.Recipe;
import com.ssafy.letcipe.domain.recipeBookmark.RecipeBookmark;
import com.ssafy.letcipe.domain.recipeComment.RecipeComment;
import com.ssafy.letcipe.domain.recipeLike.RecipeLike;
import com.ssafy.letcipe.domain.recipeStep.RecipeStep;
import com.ssafy.letcipe.domain.recipeTag.RecipeTag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class ResGetDetailRecipeDto {
    long id;
    ResGetUserDto user;
    String title;
    String content;
    int cookingTime;
    int serving;
    String category;
    String repImg;
    int recipeLike;
    boolean isLike;
    int recipeBookmark;
    boolean isBookmark;
    List<ResGetRecipeCommentDto> recipeComment;
    List<ResGetRecipeTagDto> tags;
    List<ResGetRecipeStepDto> recipeSteps;
    List<ResGetRecipeIngredientDto> ingredients;

    public ResGetDetailRecipeDto(Recipe recipe, long userId, List<ResGetRecipeIngredientDto> ingredients) {
        this.id = recipe.getId();
        this.user = ResGetUserDto.createDto(recipe.getUser());
        this.title = recipe.getTitle();
        this.content = recipe.getContent();
        this.cookingTime = recipe.getCookingTime();
        this.repImg = recipe.getRepImg();
        this.category = recipe.getCategory();
        this.recipeLike = recipe.getLikes().size();
        this.recipeBookmark = recipe.getBookmarks().size();
        this.recipeComment = new ArrayList<>();
        for (RecipeComment comment : recipe.getComments()) {
            recipeComment.add(ResGetRecipeCommentDto.createDto(comment));
        }

        this.tags = new ArrayList<>();
        for (RecipeTag tag : recipe.getTags()) {
            tags.add(new ResGetRecipeTagDto(tag));
        }

        for (RecipeLike like : recipe.getLikes()) {
            if (like.getUser().getId() == userId) {
                this.isLike = true;
                break;
            }
        }

        for (RecipeBookmark bookmark : recipe.getBookmarks()) {
            if (bookmark.getUser().getId() == userId) {
                this.isBookmark = true;
                break;
            }
        }

        this.recipeSteps = new ArrayList<>();
        for (RecipeStep step : recipe.getSteps()) {
            this.recipeSteps.add(new ResGetRecipeStepDto(step));
        }

        this.ingredients = ingredients;
    }
}