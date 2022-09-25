package com.ssafy.letcipe.api.dto.recipeComment;

import com.ssafy.letcipe.domain.recipeComment.RecipeComment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class ResGetRecipeCommentDto {
    long id;
    long userId;
    String nickname;
    String content;
    LocalDateTime regTime;
    LocalDateTime modTime;


    public static ResGetRecipeCommentDto createDto(RecipeComment recipeComment) {
        return ResGetRecipeCommentDto.builder()
                .id(recipeComment.getId())
                .nickname(recipeComment.getUser().getNickname())
                .content(recipeComment.getContent())
                .regTime(recipeComment.getRegTime())
                .modTime(recipeComment.getModTime())
                .build();
    }
}
