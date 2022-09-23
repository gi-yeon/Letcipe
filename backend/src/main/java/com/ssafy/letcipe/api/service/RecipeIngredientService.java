package com.ssafy.letcipe.api.service;

import com.ssafy.letcipe.domain.ingredient.Ingredient;
import com.ssafy.letcipe.domain.recipe.Recipe;
import com.ssafy.letcipe.domain.recipeIngredient.RecipeIngredient;
import com.ssafy.letcipe.domain.recipeIngredient.RecipeIngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public void deleteRecipeIngredients(Recipe recipe) {
        for (RecipeIngredient recipeIngredient : recipe.getIngredients()) {
            deleteRecipeIngredient(recipeIngredient);
        }
    }
    @Transactional
    public void deleteRecipeIngredient(RecipeIngredient recipeIngredient) {
        recipeIngredientRepository.deleteById(recipeIngredient.getId());
    }
}
