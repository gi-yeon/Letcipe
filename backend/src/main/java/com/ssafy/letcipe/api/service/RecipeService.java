package com.ssafy.letcipe.api.service;

import com.ssafy.letcipe.api.dto.recipe.ReqCreateRecipeDto;
import com.ssafy.letcipe.api.dto.recipeStep.ReqCreateRecipeStepDto;
import com.ssafy.letcipe.domain.recipe.Recipe;
import com.ssafy.letcipe.domain.recipe.RecipeRepository;
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
}
