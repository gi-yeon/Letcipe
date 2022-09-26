package com.ssafy.letcipe.domain.recipeLike;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ssafy.letcipe.domain.recipe.Recipe;
import com.ssafy.letcipe.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(uniqueConstraints = {
        @UniqueConstraint(
                name = "like_constraint",
                columnNames = {"recipe_id", "user_id"}
        )
})
public class RecipeLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnore
    private User user;

    @ManyToOne(targetEntity = Recipe.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id", referencedColumnName = "id")
    @JsonIgnore
    private Recipe recipe;

    @Builder
    public RecipeLike(User user, Recipe recipe) {
        this.user = user;
        this.recipe = recipe;
    }

}
