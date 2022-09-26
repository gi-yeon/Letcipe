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
import com.ssafy.letcipe.exception.AuthorityViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecipeListService {
    private final RecipeListRepository recipeListRepository;
    private final RecipeListBookmarkRepository recipeListBookmarkRepository;
    private final RecipeRepository recipeRepository;
    private final RecipeListItemRepository recipeListItemRepository;
    private final UserService userService;

    public RecipeList findRecipeList(Long recipeListId) {
        return recipeListRepository.findById(recipeListId).orElseThrow(() -> new NullPointerException("레시피 리스트를 찾을 수 없습니다."));
    }

    @Transactional
    public ResGetRecipeListDto getRecipeList(Long recipeListId) {
        RecipeList recipeList = findRecipeList(recipeListId);
        return new ResGetRecipeListDto(recipeList);
    }

    public void createRecipeList(Long userId, ReqCreateRecipeListDto reqCreateRecipeListDto) {
        User user = userService.findUser(userId);
        RecipeList recipeList = RecipeList.builder()
                .user(user)
                .name(reqCreateRecipeListDto.getName())
                .description(reqCreateRecipeListDto.getDescription())
                .isShared(reqCreateRecipeListDto.getIsShared())
                .build();
        recipeListRepository.save(recipeList);
    }

    public void updateRecipeList(Long userId, ReqUpdateRecipeListDto reqUpdateRecipeListDto, Long recipeListId) {
        RecipeList recipeList = findRecipeList(recipeListId);
        if (!userId.equals(recipeList.getUser().getId())) throw new AuthorityViolationException("수정 권한이 없습니다.");
        recipeList.updateRecipeList(reqUpdateRecipeListDto.getName(), reqUpdateRecipeListDto.getDescription(), reqUpdateRecipeListDto.getIsShared());
        recipeListRepository.save(recipeList);
    }

    public void deleteRecipeList(Long userId, Long recipeListId) {
        RecipeList recipeList = findRecipeList(recipeListId);
        if (!userId.equals(recipeList.getUser().getId())) throw new AuthorityViolationException("삭제 권한이 없습니다.");
        recipeList.deleteRecipeList();
        recipeListRepository.save(recipeList);
    }

    public void createRecipeListBookmark(Long userId, Long recipeListId) {
        User user = userService.findUser(userId);
        RecipeList recipeList = findRecipeList(recipeListId);
        if (!userId.equals(recipeList.getUser().getId())) throw new AuthorityViolationException("접근 권한이 없습니다.");
        RecipeListBookmark recipeListBookmark = RecipeListBookmark.builder()
                .user(user)
                .recipeList(recipeList)
                .build();
        recipeListBookmarkRepository.save(recipeListBookmark);
    }

    public void deleteRecipeListBookmark(Long userId, Long recipeListId) {
        RecipeListBookmark recipeListBookmark = recipeListBookmarkRepository.findByUserIdAndRecipeListId(userId, recipeListId).orElseThrow();
        if (!userId.equals(recipeListBookmark.getUser().getId())) throw new AuthorityViolationException("접근 권한이 없습니다.");
        recipeListBookmarkRepository.delete(recipeListBookmark);
    }

    public void addRecipeListItem(Long userId, ReqPostRecipeListItemDto reqPostRecipeListItemDto) {
        RecipeList recipeList = findRecipeList(reqPostRecipeListItemDto.getRecipeListId());
        if (!userId.equals(recipeList.getUser().getId())) throw new AuthorityViolationException("접근 권한이 없습니다.");
        Recipe recipe = recipeRepository.findById(reqPostRecipeListItemDto.getRecipeId()).orElseThrow();
        RecipeListItem recipeListItem = RecipeListItem.builder()
                .recipeList(recipeList)
                .recipe(recipe)
                .build();
        recipeListItemRepository.save(recipeListItem);
    }

    public void deleteRecipeListItem(Long userId, ReqDeleteRecipeListItemDto reqDeleteRecipeListItemDto) {
        RecipeListItem recipeListItem = recipeListItemRepository.findByRecipeListIdAndRecipeId(reqDeleteRecipeListItemDto.getRecipeListId(), reqDeleteRecipeListItemDto.getRecipeId());
        if (!userId.equals(recipeListItem.getRecipeList().getUser().getId()))
            throw new AuthorityViolationException("접근 권한이 없습니다.");
        recipeListItemRepository.delete(recipeListItem);
    }

    @Transactional
    public List<ResSearchRecipeListDto> searchRecipeList(Pageable pageable, String keyword) {
        List<RecipeList> searched = recipeListRepository.findByNameContaining(pageable, keyword);
        List<ResSearchRecipeListDto> result = new ArrayList<>();
        for (RecipeList recipeList : searched) {
            result.add(new ResSearchRecipeListDto(recipeList));
        }
        return result;
    }
}
