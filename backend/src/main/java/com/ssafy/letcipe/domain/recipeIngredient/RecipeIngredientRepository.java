package com.ssafy.letcipe.domain.recipeIngredient;

import com.ssafy.letcipe.domain.ingredient.Ingredient;
import com.ssafy.letcipe.domain.recipe.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, Long> {
    List<RecipeIngredient> findRecipeIngredientsByRecipe(Recipe recipe);

    @Query("select new com.ssafy.letcipe.domain.recipeIngredient.IngredientDetailAmount(ri.ingredient,ri.amount,hc.name) from RecipeIngredient ri " +
            "left join fetch Ingredient i on ri.ingredient = i " +
            "left join fetch HeaderCode hc on substring(ri.ingredient.category,1,3) = hc.id where ri.recipe = :recipe")
    List<IngredientDetailAmount> findDetailRecipeIngredientsByRecipe(Recipe recipe) throws SQLException;
}
