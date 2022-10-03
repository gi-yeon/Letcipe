package com.ssafy.letcipe.api.dto.recipeList;

import com.ssafy.letcipe.api.dto.recipeListItem.ResGetRecipeListItemDto;
import com.ssafy.letcipe.api.dto.user.ResGetWriterDto;
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
    ResGetWriterDto user;
    private String name;
    private String description;
    private int recipeListBookmark;
    private boolean isBookmark;
    private LocalDateTime regTime;
    private SharedType isShared;
    private List<ResGetRecipeListItemDto> recipeListItems;

    public ResGetRecipeListDto(RecipeList recipeList, boolean isBookmark) {
        this.id = recipeList.getId();
        this.user = new ResGetWriterDto(recipeList.getUser());
        this.name = recipeList.getName();
        this.description = recipeList.getDescription();
        this.recipeListBookmark = recipeList.getBookmarks().size();
        this.isBookmark = isBookmark;
        this.regTime = recipeList.getRegTime();
        this.isShared = recipeList.getIsShared();
        this.recipeListItems = new ArrayList<>();
        for (RecipeListItem recipeListItem : recipeList.getRecipeListItems()) {
            recipeListItems.add(new ResGetRecipeListItemDto(recipeListItem));
        }
    }
}
