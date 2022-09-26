package com.ssafy.letcipe.domain.ingredient;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    @Query("select i from Ingredient i " +
            "where i.name like concat('%',:keyword,'%')")
    public List<Ingredient> searchIngredient(String keyword);
}
