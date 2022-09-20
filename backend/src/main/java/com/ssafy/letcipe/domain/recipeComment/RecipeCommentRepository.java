package com.ssafy.letcipe.domain.recipeComment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeCommentRepository extends JpaRepository<RecipeComment, Long> {
}
