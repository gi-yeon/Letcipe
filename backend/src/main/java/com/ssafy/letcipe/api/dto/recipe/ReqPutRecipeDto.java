package com.ssafy.letcipe.api.dto.recipe;

import com.ssafy.letcipe.api.dto.recipeStep.ReqCreateRecipeStepDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqPutRecipeDto {
    List<ReqCreateRecipeStepDto> stepDtoList;
    String title,content,category;
    int cookingTime;
    int serving;
    MultipartFile repImg;
    List<String> tagList;
}
