package com.ssafy.letcipe.api.dto;

import com.ssafy.letcipe.domain.recipeList.RecipeList;
import com.ssafy.letcipe.domain.recipeList.SharedType;
import com.ssafy.letcipe.domain.recipeListItem.RecipeListItem;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class ResGetRecipeListDto {
    private Long id;
    private String nickname;
    private String name;
    private String description;
    private Date regTime;
    private SharedType isShared;
    private List<RecipeListItem> recipeListItems;

    public static ResGetRecipeListDto resGetRecipeListDto(RecipeList recipeList){
        return ResGetRecipeListDto.builder()
                .id(recipeList.getId())
                .nickname(recipeList.getUser().getNickname())
                .name(recipeList.getName())
                .description(recipeList.getDescription())
                .regTime(recipeList.getRegTime())
                .isShared(recipeList.getIsShared())
                .recipeListItems(recipeList.getRecipeListItems())
                .build();
    }
}
