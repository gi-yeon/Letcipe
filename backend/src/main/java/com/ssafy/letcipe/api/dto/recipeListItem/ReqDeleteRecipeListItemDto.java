package com.ssafy.letcipe.api.dto.recipeListItem;

import lombok.Getter;

@Getter
public class ReqDeleteRecipeListItemDto {
    private Long recipeListId;
    private Long recipeId;
}
