package com.ssafy.letcipe.api.service;

import com.ssafy.letcipe.domain.ingredient.Ingredient;
import com.ssafy.letcipe.domain.recipe.Recipe;
import com.ssafy.letcipe.domain.recipeIngredient.IngredientDetailAmount;
import com.ssafy.letcipe.domain.recipeIngredient.RecipeIngredient;
import com.ssafy.letcipe.domain.recipeIngredient.RecipeIngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecipeIngredientService {
    private final RecipeIngredientRepository recipeIngredientRepository;
    public RecipeIngredient createRecipeIngredient(Recipe recipe, Ingredient ingredient, double amount) {
        RecipeIngredient recipeIngredient = RecipeIngredient.builder()
                .recipe(recipe)
                .ingredient(ingredient)
                .amount(amount)
                .build();
        return recipeIngredientRepository.save(recipeIngredient);
    }


    @Transactional
    public void deleteRecipeIngredients(Recipe recipe) {
        for (RecipeIngredient recipeIngredient : recipe.getIngredients()) {
            System.out.println("recipeIngredient delete = " + recipeIngredient.getId());
            recipeIngredientRepository.deleteById(recipeIngredient.getId());
        }
        recipeIngredientRepository.flush();
    }

    public List<RecipeIngredient> findRecipeIngredients(Recipe recipe){
        return recipeIngredientRepository.findRecipeIngredientsByRecipe(recipe);
    }

}
