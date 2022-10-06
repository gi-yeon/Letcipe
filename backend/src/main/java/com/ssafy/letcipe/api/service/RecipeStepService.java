package com.ssafy.letcipe.api.service;

import com.ssafy.letcipe.api.dto.recipeStep.ReqPostRecipeStepDto;
import com.ssafy.letcipe.api.dto.recipeStep.ReqPutRecipeStepDto;
import com.ssafy.letcipe.domain.recipe.Recipe;
import com.ssafy.letcipe.domain.recipeStep.RecipeStep;
import com.ssafy.letcipe.domain.recipeStep.RecipeStepRepository;
import com.ssafy.letcipe.util.FileHandler;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;

@Service
@RequiredArgsConstructor
public class RecipeStepService {
    private final RecipeStepRepository recipeStepRepository;
    private final FileHandler fileHandler;

    public RecipeStep createRecipeStep(Recipe recipe, ReqPostRecipeStepDto stepDto) throws FileUploadException {
        String stepImgUrl = fileHandler.uploadImage(stepDto.getImg());
        RecipeStep recipeStep = RecipeStep.builder()
                .recipe(recipe)
                .step(stepDto.getStep())
                .content(stepDto.getContent())
                .img(stepImgUrl)
                .build();

        return recipeStepRepository.save(recipeStep);
    }
    public RecipeStep createRecipeStep(Recipe recipe, ReqPutRecipeStepDto stepDto) throws FileUploadException {
        String url;
        if (stepDto.getImg() == null || stepDto.getImg().isEmpty()) {
            url = stepDto.getImgUrl();
        } else {
            url = fileHandler.uploadImage(stepDto.getImg());
        }
        RecipeStep recipeStep = RecipeStep.builder()
                .recipe(recipe)
                .step(stepDto.getStep())
                .content(stepDto.getContent())
                .img(url)
                .build();

        return recipeStepRepository.save(recipeStep);
    }

    @Transactional
    public void deleteRecipeSteps(Recipe recipe){
        for (RecipeStep step : recipe.getSteps()) {
            try {
                fileHandler.deleteImageFile(step.getImg());
            } catch (FileNotFoundException e) {
                System.out.println("서버에 업로드된 파일이 아님");
            } finally {
                recipeStepRepository.deleteById(step.getId());
            }
        }
        recipeStepRepository.flush();
    }
}
