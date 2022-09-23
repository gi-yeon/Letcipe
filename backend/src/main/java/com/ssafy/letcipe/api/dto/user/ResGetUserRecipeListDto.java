package com.ssafy.letcipe.api.dto.user;

import com.ssafy.letcipe.domain.recipe.Recipe;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class ResGetUserRecipeListDto {
    List<ResGetUserRecipeDto> recipes;

}
