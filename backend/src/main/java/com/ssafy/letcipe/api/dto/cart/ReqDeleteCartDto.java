package com.ssafy.letcipe.api.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReqDeleteCartDto {
    Long recipeId;
}
