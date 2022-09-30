package com.ssafy.letcipe.api.dto.report;

import com.ssafy.letcipe.domain.recipe.Recipe;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JPQLCartReportDto {
    private Recipe recipe;
    private long count;
}
