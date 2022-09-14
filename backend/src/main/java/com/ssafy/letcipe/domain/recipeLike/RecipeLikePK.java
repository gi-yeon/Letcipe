package com.ssafy.letcipe.domain.recipeLike;

import com.ssafy.letcipe.domain.recipe.Recipe;
import com.ssafy.letcipe.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeLikePK implements Serializable {
    private Recipe recipe;
    private User user;
}
