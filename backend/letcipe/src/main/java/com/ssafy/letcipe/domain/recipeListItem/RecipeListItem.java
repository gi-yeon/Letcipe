package com.ssafy.letcipe.domain.recipeListItem;

import com.ssafy.letcipe.domain.recipe.Recipe;
import com.ssafy.letcipe.domain.recipeList.RecipeList;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NoArgsConstructor
@Getter
@IdClass(RecipeListItemPK.class)
public class RecipeListItem implements Serializable {

    @Id
    @ManyToOne(targetEntity = RecipeList.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_list_id", referencedColumnName = "id")
    private RecipeList recipeList;

    @Id
    @ManyToOne(targetEntity = Recipe.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id", referencedColumnName = "id")
    private Recipe recipe;

    @Column(name = "amount", nullable = false)
    private Integer amount;
}
