package com.ssafy.letcipe.domain.recipeListItem;

import com.ssafy.letcipe.domain.recipe.Recipe;
import com.ssafy.letcipe.domain.recipeList.RecipeList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeListItemPK implements Serializable {
    private RecipeList recipeList;
    private Recipe recipe;
}
