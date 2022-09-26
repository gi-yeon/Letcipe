package com.ssafy.letcipe.api.dto.ingredient;

import com.ssafy.letcipe.domain.ingredient.Ingredient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResGetIngredientDto {
    long id;
    String name;
    String category; // 코드를 이름으로 변경하여 담아줘야 함
    String measure;
    double gml;

    public ResGetIngredientDto(Ingredient ingredient, String categoryName) {
        this.id = ingredient.getId();
        this.name = ingredient.getName();
        this.category = categoryName;
        this.measure = ingredient.getMeasure();
        this.gml = ingredient.getGml();
    }
}
