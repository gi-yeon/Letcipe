package com.ssafy.letcipe.api.service;

import com.ssafy.letcipe.api.dto.recipe.ReqCreateRecipeDto;
import com.ssafy.letcipe.api.dto.recipe.ReqUpdateRecipeDto;
import com.ssafy.letcipe.api.dto.recipe.ResGetRecipeDto;
import com.ssafy.letcipe.api.dto.recipe.ResReadRecipeDto;
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
import com.ssafy.letcipe.exception.AuthorityViolationException;
import com.ssafy.letcipe.util.FileHandler;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public Recipe getRecipe(long recipeId) throws NullPointerException {
        return recipeRepository.findById(recipeId).orElseThrow(() -> new NullPointerException("레시피를 찾을 수 없습니다."));
    }


    @Transactional
    public ResReadRecipeDto readRecipe(long recipe_id,long userId) {
        Recipe recipe = getRecipe(recipe_id);
        return new ResReadRecipeDto(recipe,userId);
    }
    @Transactional
    public void createRecipe(ReqCreateRecipeDto dto, long userId) throws NullPointerException, FileUploadException {
        // 유저 찾기
        User user = userRepository.findById(userId).orElseThrow(() -> new NullPointerException("회원 정보를 찾을 수 없습니다"));
        // 레시피 대표 이미지 업로드
        String recipeImgUrl = fileHandler.uploadImage(dto.getRepImg());

        // 레시피 엔티티 생성
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
    public void updateRecipe(ReqUpdateRecipeDto updateDto, long recipe_id) throws NullPointerException, FileNotFoundException, FileUploadException {
        System.out.println("호출됨");
        // 대표 이미지 null check
        if (updateDto.getRepImg() == null || updateDto.getRepImg().isEmpty()) {
            throw new FileNotFoundException("대표 이미지가 없습니다.");
        }

        Recipe recipe = getRecipe(recipe_id);

        // 기존 대표 이미지 삭제
        fileHandler.deleteImageFile(recipe.getRepImg());
        // 새로운 대표 이미지 업로드
        String newRepImg = fileHandler.uploadImage(updateDto.getRepImg());

        // 기존 스텝 삭제
        for (int i = recipe.getSteps().size() - 1; i >= 0; i--) {
            RecipeStep step = recipe.getSteps().get(i);
            System.out.println("step = " + step);
            fileHandler.deleteImageFile(step.getImg());
            recipeStepRepository.delete(step);
        }

        // 새로운 스텝 추가
        for (ReqCreateRecipeStepDto step : updateDto.getStepDtoList()) {
            String stepImgUrl = fileHandler.uploadImage(step.getImg());
            RecipeStep recipeStep = RecipeStep.builder()
                    .recipe(recipe)
                    .step(step.getStep())
                    .content(step.getContent())
                    .img(stepImgUrl)
                    .build();

            recipeStepRepository.save(recipeStep);
        }

        // 레시피 수정
        recipe.updateRecipe(updateDto, newRepImg);
    }

    @Transactional
    public void deleteRecipe(long recipe_id, long userId) throws AuthorityViolationException {
        Recipe recipe = getRecipe(recipe_id);
        if (userId != recipe.getUser().getId()) throw new AuthorityViolationException("작성자만이 삭제할 수 있습니다.");

        // 삭제 처리
        recipe.delete();
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
    public void createLike(ReqCreateRecipeLikeDto requestDto, Long userId) throws SQLException {
        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new NullPointerException());
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
        } catch(RuntimeException e) {
            throw new SQLException("이미 좋아요 하였음.");
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
    public List<ResGetRecipeDto> searchRecipe(String keyword) throws SQLException {
        List<Recipe> searched = recipeRepository.findByKeyword(keyword);
        List<ResGetRecipeDto> result = new ArrayList<>();
        for (Recipe recipe : searched) {
            result.add(ResGetRecipeDto.createDto(recipe));
        }

        return result;
    }
}
