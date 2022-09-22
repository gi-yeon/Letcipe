package com.ssafy.letcipe.api.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqPatchCartIngredientDto {
    Long IngredientId;
    char operator;
}
