package com.ssafy.letcipe.domain.recipeListItem;

import com.ssafy.letcipe.domain.recipe.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface RecipeListItemRepository extends JpaRepository<RecipeListItem, Long> {
    RecipeListItem findByRecipeListIdAndRecipeId(Long recipeListId, Long recipeId);
    @Query("select ri from RecipeListItem ri where ri.recipeList.id=:recipe_list_id")
    List<RecipeListItem> findAllByRecipeId(long recipe_list_id);
}
