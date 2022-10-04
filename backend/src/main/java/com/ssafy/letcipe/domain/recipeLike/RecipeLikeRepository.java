package com.ssafy.letcipe.domain.recipeLike;

import com.ssafy.letcipe.domain.recipeBookmark.RecipeBookmark;
import com.ssafy.letcipe.domain.user.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeLikeRepository extends JpaRepository<RecipeLike, Long> {
    Optional<RecipeLike> findByUserIdAndRecipeId(Long userId, Long recipeId);

    List<RecipeLike> findAllByUser(Pageable pageable, User user);
}
