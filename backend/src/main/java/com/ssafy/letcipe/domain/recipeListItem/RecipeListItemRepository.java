package com.ssafy.letcipe.domain.recipeListItem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeListItemRepository extends JpaRepository<RecipeListItem, Long> {
}
