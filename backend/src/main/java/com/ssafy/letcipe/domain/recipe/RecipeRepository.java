package com.ssafy.letcipe.domain.recipe;

import com.ssafy.letcipe.domain.type.StatusType;
import com.ssafy.letcipe.domain.user.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    @Query("select distinct r from Recipe r left join fetch r.tags rp left join fetch rp.tag t " +
            "where (r.title like concat('%',:keyword,'%')" +
            "or t.name like concat('%',:keyword,'%')) and r.statusType = 0")
    List<Recipe> findByKeyword(Pageable pageable, String keyword) throws SQLException;

    @Query("select l.recipe from RecipeLike l group by l order by count(l) desc")
    List<Recipe> findBestRecipes(Pageable pageable) throws SQLException;

    List<Recipe> findAllByUser(Pageable pageable, User user);

    Optional<Recipe> findByStatusTypeAndId(StatusType n, Long boardId);

    List<Recipe> findAllByUserAndStatusType(Pageable pageable, User user, StatusType n);
}
