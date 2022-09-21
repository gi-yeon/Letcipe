package com.ssafy.letcipe.domain.recipeComment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ssafy.letcipe.domain.recipe.Recipe;
import com.ssafy.letcipe.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
public class RecipeComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    @JsonIgnore
    private Long id;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne(targetEntity = Recipe.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id", referencedColumnName = "id")
    @JsonIgnore
    private Recipe recipe;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "reg_time")
    private LocalDateTime regTime;

    @Column(name = "mod_time")
    private LocalDateTime modTime;

    @Builder
    public RecipeComment(User user, Recipe recipe, String content) {
        this.user = user;
        this.recipe = recipe;
        this.content = content;

        //등록시간 설정
        this.regTime = LocalDateTime.now();
    }

    public void updateRecipeComment(String content) throws NullPointerException {
        this.content = content;
        this.modTime = LocalDateTime.now();
    }
}
