package com.ssafy.letcipe.domain.recipeListBookmark;

import com.ssafy.letcipe.domain.recipeList.RecipeList;
import com.ssafy.letcipe.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class RecipeListBookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(targetEntity = RecipeList.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_list_id", referencedColumnName = "id")
    private RecipeList recipeList;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Builder
    public RecipeListBookmark(User user, RecipeList recipeList){
        this.user = user;
        this.recipeList = recipeList;
    }
}
