package com.ssafy.letcipe.api.dto.recipe;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResGetHotRecipeComponentDto {
    String title;
    String attribute;

    public ResGetHotRecipeComponentDto(String title, String attribute) {
        this.title = title;
        this.attribute=attribute;
    }
}
