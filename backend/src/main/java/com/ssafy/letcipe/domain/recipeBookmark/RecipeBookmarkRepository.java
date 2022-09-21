package com.ssafy.letcipe.domain.recipeBookmark;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecipeBookmarkRepository extends JpaRepository<RecipeBookmark,Long> {
    Optional<RecipeBookmark> findByUserIdAndRecipeId(Long userId,Long recipeId);
}
