package com.ssafy.letcipe.domain.recipeListBookmark;

import com.ssafy.letcipe.domain.recipeList.RecipeList;
import com.ssafy.letcipe.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeListBookmarkPK implements Serializable {
    private RecipeList recipeList;
    private User user;
}
