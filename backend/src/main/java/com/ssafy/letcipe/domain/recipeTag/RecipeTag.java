package com.ssafy.letcipe.domain.recipeTag;

import com.ssafy.letcipe.domain.tag.Tag;
import com.ssafy.letcipe.domain.recipe.Recipe;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@IdClass(RecipeTagPK.class)
public class RecipeTag {
    @Id
    @ManyToOne(targetEntity = Recipe.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id", referencedColumnName = "id")
    private Recipe recipe;

    @Id
    @ManyToOne(targetEntity = Tag.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id", referencedColumnName = "id")
    private Tag tag;



}
