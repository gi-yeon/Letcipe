package com.ssafy.letcipe.api.service;

import com.ssafy.letcipe.domain.recipe.Recipe;
import com.ssafy.letcipe.domain.recipeTag.RecipeTag;
import com.ssafy.letcipe.domain.recipeTag.RecipeTagRepository;
import com.ssafy.letcipe.domain.tag.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RecipeTagService {
    private final RecipeTagRepository recipeTagRepository;

    public RecipeTag createRecipeTag(Recipe recipe, Tag tag) {
        RecipeTag recipeTag = RecipeTag.builder()
                .recipe(recipe)
                .tag(tag)
                .build();

        return recipeTagRepository.save(recipeTag);
    }

    @Transactional
    public void deleteRecipeTags(Recipe recipe) {
        for (RecipeTag tag : recipe.getTags()) {
            deleteRecipeTag(tag);
        }
        recipeTagRepository.flush();
    }
    @Transactional
    public void deleteRecipeTag(RecipeTag recipeTag) {
        recipeTagRepository.delete(recipeTag);
    }
}
