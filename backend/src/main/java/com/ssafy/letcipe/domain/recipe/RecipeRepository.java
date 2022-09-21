package com.ssafy.letcipe.domain.recipe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    @Query("select distinct r from Recipe r left join fetch r.tags rp left join fetch rp.tag t " +
            "where r.title like concat('%',:keyword,'%')" +
            "or t.name like concat('%',:keyword,'%')")
    List<Recipe> findByKeyword(String keyword) throws SQLException;
}
