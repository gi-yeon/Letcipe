package com.ssafy.letcipe.domain.recipeList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeListRepository extends JpaRepository<RecipeList, Long> {
    List<RecipeList> findByNameContaining(String keyword);
}
