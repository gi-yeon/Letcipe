package com.ssafy.letcipe.domain.recipeList;

import com.ssafy.letcipe.domain.type.StatusType;
import com.ssafy.letcipe.domain.user.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeListRepository extends JpaRepository<RecipeList, Long> {
    List<RecipeList> findByNameContaining(Pageable pageable, String keyword);

    Integer countRecipeListByNameContaining(String keyword);
    List<RecipeList> findAllByUser(Pageable pageable, User user);

    @Query("select b.recipeList from RecipeListBookmark b where (b.recipeList.isDeleted = 0 and b.recipeList.isShared = 0) group by b.recipeList order by count(b.recipeList) desc")
    List<RecipeList> findBestRecipeLists(Pageable pageable);

    List<RecipeList> findAllByUserAndIsDeleted(Pageable pageable, User user, StatusType n);
}
