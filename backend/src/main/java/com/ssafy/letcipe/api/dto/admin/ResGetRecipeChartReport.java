package com.ssafy.letcipe.api.dto.admin;

import com.ssafy.letcipe.api.dto.recipe.ResGetRecipeDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class ResGetRecipeChartReport {
    private List<Long> recipeId;
    private List<String> recipeName;
    private List<Long> count;
}
