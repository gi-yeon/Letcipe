package com.ssafy.letcipe.api.service;

import com.ssafy.letcipe.api.dto.ingredient.ResGetIngredientDto;
import com.ssafy.letcipe.api.dto.recipe.*;
import com.ssafy.letcipe.api.dto.recipeBookmark.ReqDeleteRecipeBookmarkDto;
import com.ssafy.letcipe.api.dto.recipeBookmark.ReqPostRecipeBookmarkDto;
import com.ssafy.letcipe.api.dto.recipeIngredient.ResGetRecipeIngredientDto;
import com.ssafy.letcipe.api.dto.recipeLike.ReqDeleteRecipeLikeDto;
import com.ssafy.letcipe.api.dto.recipeLike.ReqPostRecipeLikeDto;
import com.ssafy.letcipe.api.dto.recipeStep.ReqPostRecipeStepDto;
import com.ssafy.letcipe.api.dto.recipeStep.ReqPutRecipeStepDto;
import com.ssafy.letcipe.domain.ingredient.Ingredient;
import com.ssafy.letcipe.domain.recipe.Recipe;
import com.ssafy.letcipe.domain.recipe.RecipeIngredientCountDto;
import com.ssafy.letcipe.domain.recipe.RecipeRepository;
import com.ssafy.letcipe.domain.recipe.RecipeRepositoryCustomImpl;
import com.ssafy.letcipe.domain.recipeBookmark.RecipeBookmark;
import com.ssafy.letcipe.domain.recipeBookmark.RecipeBookmarkRepository;
import com.ssafy.letcipe.domain.recipeIngredient.RecipeIngredient;
import com.ssafy.letcipe.domain.recipeLike.RecipeLike;
import com.ssafy.letcipe.domain.recipeLike.RecipeLikeRepository;
import com.ssafy.letcipe.domain.tag.Tag;
import com.ssafy.letcipe.domain.user.User;
import com.ssafy.letcipe.exception.AuthorityViolationException;
import com.ssafy.letcipe.util.FileHandler;
import com.ssafy.letcipe.util.StringUtils;
import io.netty.util.internal.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final FileHandler fileHandler;
    private final UserService userService;
    private final RecipeBookmarkRepository recipeBookmarkRepository;
    private final RecipeLikeRepository recipeLikeRepository;
    private final IngredientService ingredientService;
    private final TagService tagService;
    private final RecipeTagService recipeTagService;
    private final RecipeStepService recipeStepService;
    private final RecipeIngredientService recipeIngredientService;

    private final RecipeRepositoryCustomImpl customRepository;

    public Recipe getRecipe(long recipeId) throws NullPointerException {
        return recipeRepository.findById(recipeId).orElseThrow(() -> new NullPointerException("???????????? ?????? ??? ????????????."));
    }

    @Transactional
    public ResGetDetailRecipeDto readRecipe(long recipe_id, long userId) {
        // ????????? ??????
        Recipe recipe = getRecipe(recipe_id);
        // ????????? ?????? ?????? ??????
        List<ResGetRecipeIngredientDto> ingredientResponses = new ArrayList<>();
        // ????????? ????????? ??????(DetailCode -> CategoryName)
        for (RecipeIngredient ri : recipe.getIngredients()) {
            ResGetIngredientDto ing = ingredientService.getIngredientResponse(ri.getIngredient());
            ingredientResponses.add(new ResGetRecipeIngredientDto(ing, ri.getAmount()));
        }
        return new ResGetDetailRecipeDto(recipe, userId, ingredientResponses);
    }

    @Transactional
    public void createRecipe(ReqPostRecipeDto dto, long userId) throws NullPointerException, FileUploadException {
        // ?????? ??????
        User user = userService.findUser(userId);
        // ????????? ?????? ????????? ?????????
        String recipeImgUrl = fileHandler.uploadImage(dto.getRepImg());

        // ????????? ????????? ??????
        Recipe recipe = Recipe.builder()
                .user(user)
                .title(dto.getTitle())
                .content(dto.getContent())
                .cookingTime(dto.getCookingTime())
                .serving(dto.getServing())
                .category(dto.getCategory())
                .repImg(recipeImgUrl)
                .build();

        recipeRepository.save(recipe);

        // ?????? ?????????
        for (ReqPostRecipeStepDto step : dto.getStepDtoList()) {
            recipeStepService.createRecipeStep(recipe, step);
        }

        // ?????? ????????????
        for (ReqPostRecipeIngredientDto i : dto.getIngredients()) {
            Ingredient ingredient = ingredientService.getIngredient(i.getId());
            recipeIngredientService.createRecipeIngredient(recipe, ingredient, i.getAmount());
        }

        // ?????? ????????????
        for (String tagName : dto.getTagList()) {
            Tag tag = tagService.getOrCreateTag(tagName);
            recipeTagService.createRecipeTag(recipe, tag);
        }
    }

    @Transactional
    public void updateRecipe(ReqPutRecipeDto updateDto, long recipe_id) throws NullPointerException, FileNotFoundException, FileUploadException{
        log.info("????????????:{}",updateDto);
        Recipe recipe = getRecipe(recipe_id);

        // ?????? ????????? null check
        String newRepImg;
        if (updateDto.getRepImg() == null || updateDto.getRepImg().isEmpty()) {
            newRepImg = updateDto.getRepImgUrl();
            if (StringUtil.isNullOrEmpty(newRepImg))
                throw new FileNotFoundException("?????? ???????????? ????????????.");
        } else {
            // ????????? ?????? ????????? ?????????
            newRepImg = fileHandler.uploadImage(updateDto.getRepImg());
        }
        // ?????? ?????? ????????? ??????
        try {
            fileHandler.deleteImageFile(recipe.getRepImg());
        } catch (FileNotFoundException e) {
            log.info("????????? ???????????? ???????????? ??????");
        }

        // ?????? ?????? ??????
        recipeStepService.deleteRecipeSteps(recipe);
        // ????????? ?????? ??????
        for (ReqPutRecipeStepDto step : updateDto.getStepDtoList()) {
            recipeStepService.createRecipeStep(recipe, step);
        }

        // ?????? ????????? ?????? ??????
        recipeIngredientService.deleteRecipeIngredients(recipe);

        // ????????? ????????? ?????? ??????
        for (ReqPostRecipeIngredientDto ingredientDto : updateDto.getIngredients()) {
            // ?????? ??????
            Ingredient ingredient = ingredientService.getIngredient(ingredientDto.getId());
            // ???????????? ?????? ??????
            recipeIngredientService.createRecipeIngredient(recipe, ingredient, ingredientDto.getAmount());
        }

        // ?????? ?????? ??????
        recipeTagService.deleteRecipeTags(recipe);
        // ????????? ?????? ??????
        for (String tagName : updateDto.getTagList()) {
            recipeTagService.createRecipeTag(recipe, tagService.getOrCreateTag(tagName));
        }

        // ????????? ??????
        recipe.updateRecipe(updateDto, newRepImg);
    }

    @Transactional
    public void deleteRecipe(long recipe_id, long userId) throws AuthorityViolationException {
        Recipe recipe = getRecipe(recipe_id);
        if (userId != recipe.getUser().getId()) throw new AuthorityViolationException("??????????????? ????????? ??? ????????????.");

        // ?????? ??????
        recipe.delete();
    }

    @Transactional
    public void createBookmark(ReqPostRecipeBookmarkDto requestDto, Long userId) throws SQLException {
        User user = userService.findUser(userId);
        Recipe recipe = recipeRepository
                .findById(requestDto.getRecipeId())
                .orElseThrow(() -> new NullPointerException());
        recipeBookmarkRepository.save(
                RecipeBookmark.builder()
                        .recipe(recipe)
                        .user(user)
                        .build()
        );
    }

    @Transactional
    public void deleteBookmark(ReqDeleteRecipeBookmarkDto requestDto, Long userId) throws SQLException {
        RecipeBookmark mark = recipeBookmarkRepository
                .findByUserIdAndRecipeId(userId, requestDto.getRecipeId())
                .orElseThrow(() -> new NullPointerException());
        recipeBookmarkRepository.delete(mark);
    }

    @Transactional
    public void createLike(ReqPostRecipeLikeDto requestDto, Long userId) throws SQLException {
        User user = userService.findUser(userId);
        Recipe recipe = recipeRepository
                .findById(requestDto.getRecipeId())
                .orElseThrow(() -> new NullPointerException());

        try {
            recipeLikeRepository
                    .save(
                            RecipeLike.builder()
                                    .recipe(recipe)
                                    .user(user)
                                    .build()
                    );
        } catch (RuntimeException e) {
            throw new SQLException("?????? ????????? ?????????.");
        }
    }

    @Transactional
    public void deleteLike(ReqDeleteRecipeLikeDto requestDto, Long userId) throws SQLException {
        RecipeLike like = recipeLikeRepository
                .findByUserIdAndRecipeId(userId, requestDto.getRecipeId())
                .orElseThrow(() -> new NullPointerException());
        recipeLikeRepository.delete(like);
    }

    @Transactional
    public List<ResGetRecipeDto> searchRecipeByKeyword(Pageable pageable, String keyword) throws SQLException {
        // ????????? ????????? ?????? ??????
        List<Recipe> searched = recipeRepository.findByKeyword(pageable, keyword);
        // ????????? ?????? ?????? ?????????
        List<ResGetRecipeDto> result = new ArrayList<>();

        // ????????? ????????? ????????? ????????? ??????
        for (Recipe recipe : searched) {
            result.add(getRecipeDto(recipe));
        }

        return result;
    }

    @Transactional
    public Integer totalNumByKeyword(String keyword) throws SQLException {
        // ????????? ????????? ?????? ??????
        List<Recipe> searched = recipeRepository.totalNumByKeyword(keyword);

        return searched.size();

    }

    @Transactional
    public List<ResGetRecipeDto> searchRecipeByIngredients(Pageable pageable, String ingCSV) throws SQLException {
        // ????????? ?????? ?????? ?????????
        List<ResGetRecipeDto> result = new ArrayList<>();

        // ???????????? ??????
        String[] token = ingCSV.split(",");
        if (token.length <= 1)
            return result;

        // ????????? 1??? ?????? ???????????? ????????? ??????
        List<RecipeIngredientCountDto> recipeContainsIngredient = customRepository.findRecipeContains(token);
        // ????????? ?????? ???????????? ???????????? ?????????
        for (int i= 0 ;i<recipeContainsIngredient.size();i++) {
            if (recipeContainsIngredient.get(i).getCount() != token.length)
                recipeContainsIngredient.remove(i--);
        }
        recipeContainsIngredient.forEach(i -> {
            result.add(getRecipeDto(i.getRecipe()));
        });
        int total = result.size();
        int from = pageable.getPageNumber()*pageable.getPageSize();
        int to = from+pageable.getPageSize();
        return result.subList(from,Math.min(to,total));
    }

    @Transactional
    public Integer totalNumByIngredients(String ingCSV) throws SQLException {
        // ????????? ?????? ?????? ?????????
        List<ResGetRecipeDto> result = new ArrayList<>();

        // ???????????? ??????
        String[] token = ingCSV.split(",");
        if (token.length == 0)
            return 0;

        // ????????? 1??? ?????? ???????????? ????????? ??????
        List<RecipeIngredientCountDto> recipeContainsIngredient = customRepository.totalNumRecipeContains(token);
        // ????????? ?????? ???????????? ???????????? ?????????
        for (int i= 0 ;i<recipeContainsIngredient.size();i++) {
            if (recipeContainsIngredient.get(i).getCount() != token.length)
                recipeContainsIngredient.remove(i--);
        }

        return recipeContainsIngredient.size();
    }

    public ResGetRecipeDto getRecipeDto(Recipe recipe) {
        List<ResGetRecipeIngredientDto> recipeIngredientResponses = new ArrayList<>();
        // ????????? ?????? ????????? ??????
        List<RecipeIngredient> recipeIngredients = recipeIngredientService.findRecipeIngredients(recipe);
        for (RecipeIngredient ri : recipeIngredients) {
            ResGetIngredientDto ing = ingredientService.getIngredientResponse(ri.getIngredient());
            recipeIngredientResponses.add(new ResGetRecipeIngredientDto(ing, ri.getAmount()));
        }

        return new ResGetRecipeDto(recipe, recipeIngredientResponses);
    }

    @Transactional
    public List<ResGetRecipeDto> getBestRecipes(Pageable pageable) throws SQLException {
        List<Recipe> recipes = recipeRepository.findBestRecipes(pageable);
        List<ResGetRecipeDto> result = new ArrayList<>();
        for (Recipe recipe : recipes) {
            result.add(getRecipeDto(recipe));
        }
        return result;
    }


}
