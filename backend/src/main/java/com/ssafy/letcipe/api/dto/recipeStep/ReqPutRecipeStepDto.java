package com.ssafy.letcipe.api.dto.recipeStep;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import reactor.util.annotation.Nullable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqPutRecipeStepDto {
    private int step;
    private String content;
    // multipartFile 혹은 string 둘 중 하나로 이미지 전달
    @Nullable
    private MultipartFile img;
    @Nullable
    private String imgUrl;
}
