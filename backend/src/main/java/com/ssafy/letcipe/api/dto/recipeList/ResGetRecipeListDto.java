package com.ssafy.letcipe.api.dto.recipeList;

import com.ssafy.letcipe.api.dto.recipeListItem.ResGetRecipeListItemDto;
import com.ssafy.letcipe.domain.recipeList.RecipeList;
import com.ssafy.letcipe.domain.recipeList.SharedType;
import com.ssafy.letcipe.domain.recipeListItem.RecipeListItem;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
public class ResGetRecipeListDto {
    private Long id;
    private String nickname;
    private String name;
    private String description;
    private LocalDateTime regTime;
    private SharedType isShared;
    private List<ResGetRecipeListItemDto> recipeListItems;

    public ResGetRecipeListDto(RecipeList recipeList) {
        this.id = recipeList.getId();
        this.nickname = recipeList.getUser().getNickname();
        this.name = recipeList.getName();
        this.description = recipeList.getDescription();
        this.regTime = recipeList.getRegTime();
        this.isShared = recipeList.getIsShared();
        this.recipeListItems = new ArrayList<>();
        for (RecipeListItem recipeListItem : recipeList.getRecipeListItems()) {
            recipeListItems.add(new ResGetRecipeListItemDto(recipeListItem));
        }
    }
}
