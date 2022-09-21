package com.ssafy.letcipe.domain.recipeListBookmark;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecipeListBookmarkRepository extends JpaRepository<RecipeListBookmark, Long> {
    Optional<RecipeListBookmark> findByUserIdAndRecipeListId(Long userId, Long recipeListId);
}
