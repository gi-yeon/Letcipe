package com.ssafy.letcipe.api.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class ResGetUserRecipesDto {
    List<ResGetUserRecipeDto> recipes;

}
