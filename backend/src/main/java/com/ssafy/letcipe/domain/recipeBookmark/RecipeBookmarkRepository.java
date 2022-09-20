package com.ssafy.letcipe.domain.recipeBookmark;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeBookmarkRepository extends JpaRepository<RecipeBookmark, Long> {
}
