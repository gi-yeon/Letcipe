package com.ssafy.letcipe.domain.recipeListBookmark;

import com.ssafy.letcipe.domain.user.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeListBookmarkRepository extends JpaRepository<RecipeListBookmark, Long> {
    Optional<RecipeListBookmark> findByUserIdAndRecipeListId(Long userId, Long recipeListId);

    List<RecipeListBookmark> findAllByUser(Pageable pageable, User user);
}
