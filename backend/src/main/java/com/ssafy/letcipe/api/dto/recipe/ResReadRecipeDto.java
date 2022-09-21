package com.ssafy.letcipe.api.dto.recipe;

import com.ssafy.letcipe.api.dto.recipeComment.ResGetRecipeCommentDto;
import com.ssafy.letcipe.api.dto.user.ResGetUserDto;
import com.ssafy.letcipe.domain.recipe.Recipe;
import com.ssafy.letcipe.domain.recipeBookmark.RecipeBookmark;
import com.ssafy.letcipe.domain.recipeComment.RecipeComment;
import com.ssafy.letcipe.domain.recipeLike.RecipeLike;
import com.ssafy.letcipe.domain.recipeTag.RecipeTag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class ResReadRecipeDto {
    long id;
    ResGetUserDto user;
    String title;
    String content;
    int cookingTime;
    int serving;
    String repImg;
    int recipeLike;
    boolean isLike;
    int recipeBookmark;
    boolean isBookmark;
    private List<ResGetRecipeCommentDto> recipeComment;

    private List<RecipeTag> tags;

    public ResReadRecipeDto(Recipe recipe,long userId) {
        this.id = recipe.getId();
        this.user = ResGetUserDto.createDto(recipe.getUser());
        this.title = recipe.getTitle();
        this.content = recipe.getContent();
        this.cookingTime = recipe.getCookingTime();
        this.repImg = recipe.getRepImg();
        this.recipeLike = recipe.getLikes().size();
        this.recipeBookmark = recipe.getBookmarks().size();
        this.recipeComment = new ArrayList<>();
        for (RecipeComment comment : recipe.getComments()) {
            recipeComment.add(ResGetRecipeCommentDto.createDto(comment));
        }

        this.tags = recipe.getTags();

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

    }
}