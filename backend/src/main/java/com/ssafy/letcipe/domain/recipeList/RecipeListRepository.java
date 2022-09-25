package com.ssafy.letcipe.domain.recipeList;

import com.ssafy.letcipe.domain.user.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeListRepository extends JpaRepository<RecipeList, Long> {
    List<RecipeList> findByNameContaining(String keyword);

    List<RecipeList> findAllByUser(Pageable pageable, User user);
}
