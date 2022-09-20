package com.ssafy.letcipe.api.controller;

import com.ssafy.letcipe.api.dto.recipe.ReqCreateRecipeDto;
import com.ssafy.letcipe.api.dto.recipe.ReqUpdateRecipeDto;
import com.ssafy.letcipe.api.dto.recipeBookmark.ReqCreateRecipeBookmarkDto;
import com.ssafy.letcipe.api.dto.recipeBookmark.ReqDeleteRecipeBookmarkDto;
import com.ssafy.letcipe.api.dto.recipeComment.ReqCreateRecipeCommentDto;
import com.ssafy.letcipe.api.dto.recipeComment.ReqUpdateRecipeCommentDto;
import com.ssafy.letcipe.api.dto.recipeLike.ReqCreateRecipeLikeDto;
import com.ssafy.letcipe.api.dto.recipeLike.ReqDeleteRecipeLikeDto;
import com.ssafy.letcipe.api.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.sql.SQLException;

@RestController
@RequestMapping("/recipe")
@RequiredArgsConstructor
public class RecipeController {
    private final RecipeService recipeService;
    private final Logger logger = LoggerFactory.getLogger(RecipeController.class);

    @PostMapping("")
    public ResponseEntity createRecipe(@ModelAttribute ReqCreateRecipeDto createDto) throws FileUploadException {
        int userId = 1; // TODO 토큰에서 유저 id 가져와야 함
        recipeService.createRecipe(createDto,userId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{recipe_id}")
    public ResponseEntity updateRecipe(@PathVariable long recipe_id, @ModelAttribute ReqUpdateRecipeDto updateDto) throws FileNotFoundException, FileUploadException {
        int userId = 1; // TODO 토큰에서 유저 id 가져와야 함
        logger.info("요청변수:{},{}",recipe_id, updateDto);
        recipeService.updateRecipe(updateDto,recipe_id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/like")
    ResponseEntity createRecipeLike(@RequestBody ReqCreateRecipeLikeDto requestDto) throws SQLException {
        recipeService.createLike(requestDto, 1L);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/like")
    ResponseEntity deleteRecipeLike(@RequestBody ReqDeleteRecipeLikeDto requestDto) throws SQLException {
        recipeService.deleteLike(requestDto, 1L);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/mark")
    ResponseEntity createRecipeBookmark(@RequestBody ReqCreateRecipeBookmarkDto requestDto) throws SQLException {
        recipeService.createBookmark(requestDto, 1L);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/mark")
    ResponseEntity deleteRecipeBookmark(@RequestBody ReqDeleteRecipeBookmarkDto requestDto) throws SQLException {
        recipeService.deleteBookmark(requestDto, 1L);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/comment")
    ResponseEntity createRecipeComment(@RequestBody ReqCreateRecipeCommentDto requestDto) throws SQLException {
        recipeService.createComment(requestDto, 1L);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/comment")
    ResponseEntity updateRecipeComment(@RequestBody ReqUpdateRecipeCommentDto requestDto) throws SQLException {
        recipeService.updateComment(requestDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/comment/{recipeCommentId}")
    ResponseEntity deleteRecipeComment(@PathVariable Long recipeCommentId) throws SQLException {
        recipeService.deleteComment(recipeCommentId);
        return ResponseEntity.ok().build();
    }


}
