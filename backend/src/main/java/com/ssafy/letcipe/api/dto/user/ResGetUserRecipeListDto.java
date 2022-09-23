package com.ssafy.letcipe.api.dto.user;

import com.ssafy.letcipe.api.dto.recipeListItem.ResGetRecipeListItemDto;
import com.ssafy.letcipe.domain.recipe.Recipe;
import com.ssafy.letcipe.domain.recipeList.RecipeList;
import com.ssafy.letcipe.domain.recipeList.SharedType;
import com.ssafy.letcipe.domain.recipeListItem.RecipeListItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class ResGetUserRecipeListDto {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime regTime;
    private SharedType isShared;

    public ResGetUserRecipeListDto(RecipeList recipeList) {
        this.id = recipeList.getId();
        this.name = recipeList.getName();
        this.description = recipeList.getDescription();
        this.regTime = recipeList.getRegTime();
        this.isShared = recipeList.getIsShared();
    }
}