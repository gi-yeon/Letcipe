package com.ssafy.letcipe.api.dto.report;

import com.ssafy.letcipe.api.dto.recipe.ResGetRecipeDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
public class ResGetCartReport {
    private ResGetRecipeDto recipe;
    private long count;
}
