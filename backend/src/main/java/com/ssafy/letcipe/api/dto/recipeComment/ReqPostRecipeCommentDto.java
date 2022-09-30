package com.ssafy.letcipe.api.dto.recipeComment;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReqPostRecipeCommentDto {
    Long recipeId;
    String content;
}
