package com.ssafy.letcipe.domain.recipeListBookmark;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeListBookmarkRepository extends JpaRepository<RecipeListBookmark, Long> {
}
