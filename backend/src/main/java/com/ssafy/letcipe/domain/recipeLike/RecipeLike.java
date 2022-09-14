package com.ssafy.letcipe.domain.recipeLike;

import com.ssafy.letcipe.domain.ingredient.Ingredient;
import com.ssafy.letcipe.domain.recipe.Recipe;
import com.ssafy.letcipe.domain.tag.Tag;
import com.ssafy.letcipe.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@IdClass(RecipeLikePK.class)
public class RecipeLike {

    @Id
    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Id
    @ManyToOne(targetEntity = Recipe.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id", referencedColumnName = "id")
    private Recipe recipe;
}
