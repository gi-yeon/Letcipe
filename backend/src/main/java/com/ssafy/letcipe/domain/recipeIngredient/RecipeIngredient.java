package com.ssafy.letcipe.domain.recipeIngredient;

import com.ssafy.letcipe.domain.ingredient.Ingredient;
import com.ssafy.letcipe.domain.recipe.Recipe;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class RecipeIngredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @ManyToOne(targetEntity = Recipe.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id", referencedColumnName = "id")
    private Recipe recipe;

    @ManyToOne(targetEntity = Ingredient.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_id", referencedColumnName = "id")
    private Ingredient ingredient;

    @Column(name = "amount")
    private Double amount;

    @Builder
    public RecipeIngredient(Recipe recipe, Ingredient ingredient, Double amount) {
        this.recipe = recipe;
        this.ingredient = ingredient;
        this.amount = amount;
    }
}
