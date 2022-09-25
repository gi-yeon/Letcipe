package com.ssafy.letcipe.domain.recipeBookmark;

import com.ssafy.letcipe.domain.user.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeBookmarkRepository extends JpaRepository<RecipeBookmark,Long> {
    Optional<RecipeBookmark> findByUserIdAndRecipeId(Long userId,Long recipeId);
    List<RecipeBookmark> findAllByUser(Pageable pageable, User user);
}
