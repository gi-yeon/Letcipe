package com.ssafy.letcipe.domain.recipe;


import com.ssafy.letcipe.domain.ingredient.Ingredient;
import org.springframework.data.domain.Pageable;

import java.sql.SQLException;
import java.util.List;

public interface RecipeRepositoryCustom {

    public List<RecipeIngredientCountDto> findRecipeContains(Pageable pageable, String[] ingredientIds) throws SQLException;

}
