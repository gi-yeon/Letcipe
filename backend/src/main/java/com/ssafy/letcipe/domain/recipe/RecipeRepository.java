package com.ssafy.letcipe.domain.recipe;

import com.ssafy.letcipe.domain.type.StatusType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    Optional<Recipe> findByStatusTypeAndId(StatusType statusType, Long id);
}
