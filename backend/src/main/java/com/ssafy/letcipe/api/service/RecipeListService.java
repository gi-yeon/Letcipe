package com.ssafy.letcipe.api.service;

import com.ssafy.letcipe.api.dto.recipeList.ReqCreateRecipeListDto;
import com.ssafy.letcipe.api.dto.recipeList.ReqUpdateRecipeListDto;
import com.ssafy.letcipe.api.dto.recipeList.ResGetRecipeListDto;
import com.ssafy.letcipe.api.dto.recipeList.ResSearchRecipeListDto;
import com.ssafy.letcipe.api.dto.recipeListItem.ReqPostRecipeListItemDto;
import com.ssafy.letcipe.api.dto.recipeListItem.ReqDeleteRecipeListItemDto;
import com.ssafy.letcipe.domain.recipe.Recipe;
import com.ssafy.letcipe.domain.recipe.RecipeRepository;
import com.ssafy.letcipe.domain.recipeList.RecipeList;
import com.ssafy.letcipe.domain.recipeList.RecipeListRepository;
import com.ssafy.letcipe.domain.recipeListBookmark.RecipeListBookmark;
import com.ssafy.letcipe.domain.recipeListBookmark.RecipeListBookmarkRepository;
import com.ssafy.letcipe.domain.recipeListItem.RecipeListItem;
import com.ssafy.letcipe.domain.recipeListItem.RecipeListItemRepository;
import com.ssafy.letcipe.domain.user.User;
import com.ssafy.letcipe.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecipeListService {
    private final RecipeListRepository recipeListRepository;
    private final UserRepository userRepository;
    private final RecipeListBookmarkRepository recipeListBookmarkRepository;
    private final RecipeRepository recipeRepository;
    private final RecipeListItemRepository recipeListItemRepository;

    public RecipeList findRecipeList(Long recipeListId) {
        return recipeListRepository.findById(recipeListId).orElseThrow(() -> new NullPointerException("레시피 리스트를 찾을 수 없습니다."));
    }

    @Transactional
    public ResGetRecipeListDto getRecipeList(Long recipeListId) {
        RecipeList recipeList = findRecipeList(recipeListId);
        return new ResGetRecipeListDto(recipeList);
    }

    public void createRecipeList(String accessToken, ReqCreateRecipeListDto reqCreateRecipeListDto) {
        User user = userRepository.findById(1L).orElseThrow();
        RecipeList recipeList = RecipeList.builder()
                .user(user)
                .name(reqCreateRecipeListDto.getName())
                .description(reqCreateRecipeListDto.getDescription())
                .isShared(reqCreateRecipeListDto.getIsShared())
                .build();
        recipeListRepository.save(recipeList);
    }

    public void updateRecipeList(String accessToken, ReqUpdateRecipeListDto reqUpdateRecipeListDto, Long recipeListId) {
        User user = userRepository.findById(1L).orElseThrow();
        RecipeList recipeList = recipeListRepository.findById(recipeListId).orElseThrow();
        recipeList.updateRecipeList(reqUpdateRecipeListDto.getName(), reqUpdateRecipeListDto.getDescription(), reqUpdateRecipeListDto.getIsShared());
        recipeListRepository.save(recipeList);
    }

    public void deleteRecipeList(String accessToken, Long recipeListId) {
        User user = userRepository.findById(1L).orElseThrow();
        RecipeList recipeList = recipeListRepository.findById(recipeListId).orElseThrow();
        recipeList.deleteRecipeList();
        recipeListRepository.save(recipeList);
    }

    public void createRecipeListBookmark(String accessToken, Long recipeListId) {
        User user = userRepository.findById(1L).orElseThrow();
        RecipeList recipeList = recipeListRepository.findById(recipeListId).orElseThrow();
        RecipeListBookmark recipeListBookmark = RecipeListBookmark.builder()
                .user(user)
                .recipeList(recipeList)
                .build();
        recipeListBookmarkRepository.save(recipeListBookmark);
    }

    public void deleteRecipeListBookmark(String accessToken, Long recipeListId) {
        User user = userRepository.findById(1L).orElseThrow();
        RecipeListBookmark recipeListBookmark = recipeListBookmarkRepository.findByUserIdAndRecipeListId(1L, recipeListId).orElseThrow();
        recipeListBookmarkRepository.delete(recipeListBookmark);
    }

    public void addRecipeListItem(String accessToken, ReqPostRecipeListItemDto reqPostRecipeListItemDto) {
        User user = userRepository.findById(1L).orElseThrow();
        RecipeList recipeList = recipeListRepository.findById(reqPostRecipeListItemDto.getRecipeListId()).orElseThrow();
        Recipe recipe = recipeRepository.findById(reqPostRecipeListItemDto.getRecipeId()).orElseThrow();
        RecipeListItem recipeListItem = RecipeListItem.builder()
                .recipeList(recipeList)
                .recipe(recipe)
                .build();
        recipeListItemRepository.save(recipeListItem);
    }

    public void deleteRecipeListItem(String accessToken, ReqDeleteRecipeListItemDto reqDeleteRecipeListItemDto) {
        User user = userRepository.findById(1L).orElseThrow();
        RecipeListItem recipeListItem = recipeListItemRepository.findByRecipeListIdAndRecipeId(reqDeleteRecipeListItemDto.getRecipeListId(), reqDeleteRecipeListItemDto.getRecipeId());
        recipeListItemRepository.delete(recipeListItem);
    }

    @Transactional
    public List<ResSearchRecipeListDto> searchRecipeList(String keyword) {
        List<RecipeList> searched = recipeListRepository.findByNameContaining(keyword);
        List<ResSearchRecipeListDto> result = new ArrayList<>();
        for (RecipeList recipeList : searched) {
            result.add(new ResSearchRecipeListDto(recipeList));
        }
        return result;
    }
}
