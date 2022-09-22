package com.ssafy.letcipe.api.dto.recipeStep;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqCreateRecipeStepDto {
    private int step;
    private String content;
    private MultipartFile img;
}
