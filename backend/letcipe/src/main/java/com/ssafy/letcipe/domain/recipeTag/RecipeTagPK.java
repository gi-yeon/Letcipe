package com.ssafy.letcipe.domain.recipeTag;

import com.ssafy.letcipe.domain.recipe.Recipe;
import com.ssafy.letcipe.domain.tag.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeTagPK implements Serializable {
    private Recipe recipe;
    private Tag tag;

}
