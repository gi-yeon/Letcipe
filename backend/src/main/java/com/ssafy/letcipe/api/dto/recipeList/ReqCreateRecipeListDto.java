package com.ssafy.letcipe.api.dto.recipeList;

import com.ssafy.letcipe.domain.recipeList.SharedType;
import lombok.Getter;

@Getter
public class ReqCreateRecipeListDto {
    private String name;
    private String description;
    private SharedType isShared;
}
