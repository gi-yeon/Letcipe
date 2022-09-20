package com.ssafy.letcipe.api.service;

import com.ssafy.letcipe.api.dto.recipe.ReqCreateRecipeDto;
import com.ssafy.letcipe.api.dto.recipeBookmark.ReqCreateRecipeBookmarkDto;
import com.ssafy.letcipe.api.dto.recipeBookmark.ReqDeleteRecipeBookmarkDto;
import com.ssafy.letcipe.api.dto.recipeComment.ReqCreateRecipeCommentDto;
import com.ssafy.letcipe.api.dto.recipeComment.ReqUpdateRecipeCommentDto;
import com.ssafy.letcipe.api.dto.recipeLike.ReqCreateRecipeLikeDto;
import com.ssafy.letcipe.api.dto.recipeLike.ReqDeleteRecipeLikeDto;
import com.ssafy.letcipe.api.dto.recipeStep.ReqCreateRecipeStepDto;
import com.ssafy.letcipe.domain.recipe.Recipe;
import com.ssafy.letcipe.domain.recipe.RecipeRepository;
import com.ssafy.letcipe.domain.recipeBookmark.RecipeBookmark;
import com.ssafy.letcipe.domain.recipeBookmark.RecipeBookmarkRepository;
import com.ssafy.letcipe.domain.recipeComment.RecipeComment;
import com.ssafy.letcipe.domain.recipeComment.RecipeCommentRepository;
import com.ssafy.letcipe.domain.recipeLike.RecipeLike;
import com.ssafy.letcipe.domain.recipeLike.RecipeLikeRepository;
import com.ssafy.letcipe.domain.recipeStep.RecipeStep;
import com.ssafy.letcipe.domain.recipeStep.RecipeStepRepository;
import com.ssafy.letcipe.domain.user.User;
import com.ssafy.letcipe.domain.user.UserRepository;
import com.ssafy.letcipe.util.FileHandler;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

@Service
@RequiredArgsConstructor
public class RecipeService {
    private final RecipeStepRepository recipeStepRepository;
    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;
    private final FileHandler fileHandler;
    private final RecipeCommentRepository recipeCommentRepository;
    private final RecipeBookmarkRepository recipeBookmarkRepository;
    private final RecipeLikeRepository recipeLikeRepository;
    @Transactional
    public void createRecipe(long userId,ReqCreateRecipeDto dto) throws NullPointerException, FileUploadException {
        // 유저 찾기
        User user = userRepository.findById(userId).orElseThrow(()-> new NullPointerException("회원 정보를 찾을 수 없습니다"));
        // 레시피 대표 이미지 업로드
        String recipeImgUrl = fileHandler.uploadImage(dto.getRepImg());

        // 레시피 엔티티 생성
        Recipe recipe = Recipe.builder()
                .user(user)
                .title(dto.getTitle())
                .content(dto.getContent())
                .serving(dto.getServing())
                .category(dto.getCategory())
                .repImg(recipeImgUrl)
                .build();

        recipeRepository.save(recipe);

        // 스텝 만들기
        for (ReqCreateRecipeStepDto step : dto.getStepDtoList()) {
            String stepImgUrl = fileHandler.uploadImage(step.getImg());
            RecipeStep recipeStep = RecipeStep.builder()
                    .recipe(recipe)
                    .step(step.getStep())
                    .content(step.getContent())
                    .img(stepImgUrl)
                    .build();

            recipeStepRepository.save(recipeStep);
        }
    }
    @Transactional
    public void deleteComment(Long recipeCommentId) throws SQLException {
        RecipeComment comment = recipeCommentRepository
                .findById(recipeCommentId)
                .orElseThrow(() -> new NullPointerException());
        recipeCommentRepository.delete(comment);
    }

    @Transactional
    public void updateComment(ReqUpdateRecipeCommentDto requestDto) throws SQLException {
        RecipeComment comment = recipeCommentRepository
                .findById(requestDto.getRecipeCommentId())
                .orElseThrow(() -> new NullPointerException());
        comment.updateRecipeComment(requestDto.getContent());
    }

    @Transactional
    public void createComment(ReqCreateRecipeCommentDto requestDto, Long userId) throws SQLException {
        System.out.println("recipe id : " + requestDto.getRecipeId());
        System.out.println("content: " + requestDto.getContent());
        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new NullPointerException());
        Recipe recipe = recipeRepository
                .findById(requestDto.getRecipeId())
                .orElseThrow(() -> new NullPointerException());
        recipeCommentRepository.save(
                RecipeComment.builder()
                        .recipe(recipe)
                        .content(requestDto.getContent())
                        .user(user)
                        .build());
    }

    @Transactional
    public void createBookmark(ReqCreateRecipeBookmarkDto requestDto, Long userId) throws SQLException {
        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new NullPointerException());
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
    public void createLike(ReqCreateRecipeLikeDto requestDto, Long userId) throws SQLException{
        User user=userRepository
                .findById(userId)
                .orElseThrow(()->new NullPointerException());
        Recipe recipe=recipeRepository
                .findById(requestDto.getRecipeId())
                .orElseThrow(()->new NullPointerException());
        recipeLikeRepository
                .save(
                        RecipeLike.builder()
                                .recipe(recipe)
                                .user(user)
                                .build()
                );
    }

    @Transactional
    public void deleteLike(ReqDeleteRecipeLikeDto requestDto, Long userId) throws SQLException{
        RecipeLike like=recipeLikeRepository
                .findByUserIdAndRecipeId(userId, requestDto.getRecipeId())
                .orElseThrow(()->new NullPointerException());
        recipeLikeRepository.delete(like);
    }
}
