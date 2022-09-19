package com.ssafy.letcipe.api.service;

import com.ssafy.letcipe.api.dto.ResGetRecipeListDto;
import com.ssafy.letcipe.domain.recipeList.RecipeList;
import com.ssafy.letcipe.domain.recipeList.RecipeListRepository;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@NoArgsConstructor
public class RecipeListService {
    private RecipeListRepository recipeListRepository;

    public ResGetRecipeListDto getRecipeList(Long id){
        RecipeList recipeList = recipeListRepository.findById(id).orElseThrow();
        return ResGetRecipeListDto.resGetRecipeListDto(recipeList);
    }
}
