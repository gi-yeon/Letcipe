package com.ssafy.letcipe.api.dto.recipe;

import com.ssafy.letcipe.api.dto.recipeStep.ReqPostRecipeStepDto;
import com.ssafy.letcipe.api.dto.recipeStep.ReqPutRecipeStepDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import reactor.util.annotation.Nullable;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqPutRecipeDto {
    List<ReqPutRecipeStepDto> stepDtoList;
    String title,content,category;
    int cookingTime;
    int serving;
    @Nullable
    MultipartFile repImg;
    @Nullable
    String repImgUrl;
    List<ReqPostRecipeIngredientDto> ingredients;
    List<String> tagList;
}
