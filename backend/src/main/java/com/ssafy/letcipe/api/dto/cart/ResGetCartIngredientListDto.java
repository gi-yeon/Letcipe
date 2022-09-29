package com.ssafy.letcipe.api.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResGetCartIngredientListDto {
    List<ResGetCartIngredientDto> list;
}
