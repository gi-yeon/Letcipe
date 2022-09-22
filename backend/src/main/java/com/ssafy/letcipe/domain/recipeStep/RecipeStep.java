package com.ssafy.letcipe.domain.recipeStep;

import com.ssafy.letcipe.domain.recipe.Recipe;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class RecipeStep {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(targetEntity = Recipe.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id",  referencedColumnName = "id")
    private Recipe recipe;

    @Column(name = "step", nullable = false)
    private Integer step;

    @Column(name = "content", length = 100, nullable = false)
    private String content;

    @Column(name = "img")
    private String img;
}
