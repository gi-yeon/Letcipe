package com.ssafy.letcipe.api.controller;

import com.ssafy.letcipe.api.dto.recipe.ReqCreateRecipeDto;
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
        recipeService.createRecipe(userId, createDto);
        return ResponseEntity.ok("");
    }
    @PostMapping("/like")
    ResponseEntity<?> createRecipeLike(@RequestBody ReqCreateRecipeLikeDto requestDto){
        HttpStatus status=HttpStatus.OK;
        try{
            recipeService.createLike(requestDto,1L);
        }catch (SQLException | NullPointerException e){
            System.out.println(e.getMessage());
            status=HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(status);
    }

    @DeleteMapping("/like")
    ResponseEntity<?> deleteRecipeLike(@RequestBody ReqDeleteRecipeLikeDto requestDto){
        HttpStatus status=HttpStatus.OK;
        try{
            recipeService.deleteLike(requestDto,1L);
        }catch(SQLException | NullPointerException e){
            System.out.println(e.getMessage());
            status=HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(status);
    }

    @PostMapping("/mark")
    ResponseEntity<?> createRecipeBookmark(@RequestBody ReqCreateRecipeBookmarkDto requestDto){
        HttpStatus status=HttpStatus.OK;
        try{
            recipeService.createBookmark(requestDto,1L);
        }catch(SQLException | NullPointerException e){
            System.out.println(e.getMessage());
            status=HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(status);
    }

    @DeleteMapping("/mark")
    ResponseEntity<?> deleteRecipeBookmark(@RequestBody ReqDeleteRecipeBookmarkDto requestDto){
        HttpStatus status=HttpStatus.OK;
        try{
            recipeService.deleteBookmark(requestDto,1L);
        }catch(SQLException | NullPointerException e){
            System.out.println(e.getMessage());
            status=HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(status);
    }

    @PostMapping("/comment")
    ResponseEntity<?> createRecipeComment(@RequestBody ReqCreateRecipeCommentDto requestDto){
        HttpStatus status=HttpStatus.OK;
        try{
            recipeService.createComment(requestDto,1L);
        }catch(SQLException | NullPointerException e){
            status=HttpStatus.BAD_REQUEST;
            e.printStackTrace();
        }
        return new ResponseEntity<>(status);
    }

    @PutMapping("/comment")
    ResponseEntity<?> updateRecipeComment(@RequestBody ReqUpdateRecipeCommentDto requestDto){
        HttpStatus status=HttpStatus.OK;
        try{
            recipeService.updateComment(requestDto);
        }catch(SQLException | NullPointerException e){
            status=HttpStatus.BAD_REQUEST;
            e.printStackTrace();
        }
        return new ResponseEntity<>(status);
    }

    @DeleteMapping("/comment/{recipeCommentId}")
    ResponseEntity<?> deleteRecipeComment(@PathVariable Long recipeCommentId){
        HttpStatus status= HttpStatus.OK;
        try{
            recipeService.deleteComment(recipeCommentId);
        }catch (SQLException | NullPointerException e){
            status=HttpStatus.BAD_REQUEST;
            e.printStackTrace();
        }
        return new ResponseEntity<>(status);
    }
}
