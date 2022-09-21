package com.ssafy.letcipe.domain.user;

import com.ssafy.letcipe.domain.recipeBookmark.RecipeBookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByNickname(String nickname);
    Optional<User> findByUserId(String userId);
}
