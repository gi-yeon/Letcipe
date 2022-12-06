package com.ssafy.letcipe.api.dto.recipe;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqPostRecipeIngredientDto {
    long id;
    double amount;
}
