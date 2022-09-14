package com.ssafy.letcipe.domain.recipeListBookmark;

import com.ssafy.letcipe.domain.recipeList.RecipeList;
import com.ssafy.letcipe.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@IdClass(RecipeListBookmarkPK.class)
public class RecipeListBookmark {

    @Id
    @ManyToOne(targetEntity = RecipeList.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_list_id", referencedColumnName = "id")
    private RecipeList recipeList;

    @Id
    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

}
