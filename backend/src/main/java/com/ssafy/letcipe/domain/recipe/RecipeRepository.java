package com.ssafy.letcipe.domain.recipe;

import com.ssafy.letcipe.domain.user.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    @Query("select distinct r from Recipe r left join fetch r.tags rp left join fetch rp.tag t " +
            "where (r.title like concat('%',:keyword,'%')" +
            "or t.name like concat('%',:keyword,'%')) and r.isDeleted = 0")
    List<Recipe> findByKeyword(Pageable pageable, String keyword) throws SQLException;

    // select r.title, count(l.user_id)
    // from recipe r right join recipe_like l on r.id = l.recipe_id group by r.id order by count(l.user_id) desc;
    @Query("select r from RecipeLike l left join Recipe r on r = l.recipe group by r order by count(l) desc")
    List<Recipe> findBestRecipes(Pageable pageable) throws SQLException;
}
