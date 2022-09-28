package com.ssafy.letcipe.api.controller;


import com.ssafy.letcipe.api.dto.recipe.ReqPostRecipeDto;
import com.ssafy.letcipe.api.dto.recipe.ReqPutRecipeDto;
import com.ssafy.letcipe.api.dto.recipe.ResGetDetailRecipeDto;
import com.ssafy.letcipe.api.dto.recipeBookmark.ReqPostRecipeBookmarkDto;
import com.ssafy.letcipe.api.dto.recipeBookmark.ReqDeleteRecipeBookmarkDto;
import com.ssafy.letcipe.api.dto.recipeLike.ReqPostRecipeLikeDto;
import com.ssafy.letcipe.api.dto.recipeLike.ReqDeleteRecipeLikeDto;
import com.ssafy.letcipe.api.service.RecipeService;
import com.ssafy.letcipe.util.StringUtils;
import io.netty.util.internal.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.sql.SQLException;

@RestController
@RequestMapping("/recipe")
@RequiredArgsConstructor
@Log4j2
public class RecipeController {
    private final RecipeService recipeService;

    @GetMapping("/{recipe_id}")
    @Transactional
    public ResponseEntity readRecipe(@PathVariable long recipe_id) {
        long userId = 1L; // TODO 토큰에서 유저 id 가져와야 함, 없다면 -1 등으로 표기
        ResGetDetailRecipeDto recipe = recipeService.readRecipe(recipe_id, userId);
        return ResponseEntity.ok(recipe);
    }

    @PostMapping("")
    public ResponseEntity createRecipe(@ModelAttribute ReqPostRecipeDto dto) throws FileUploadException {
        int userId = 1; // TODO 토큰에서 유저 id 가져와야 함
        recipeService.createRecipe(dto, userId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{recipe_id}")
    public ResponseEntity updateRecipe(@PathVariable long recipe_id, @ModelAttribute ReqPutRecipeDto dto) throws FileNotFoundException, FileUploadException {
        int userId = 1; // TODO 토큰에서 유저 id 가져와야 함
        recipeService.updateRecipe(dto, recipe_id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{recipe_id}")
    public ResponseEntity deleteRecipe(@PathVariable long recipe_id) {
        long userId = 1L; // TODO 토큰에서 유저 id 가져와야 함
        recipeService.deleteRecipe(recipe_id, userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/like")
    ResponseEntity createRecipeLike(@RequestBody ReqPostRecipeLikeDto requestDto) throws SQLException {
        try {
            recipeService.createLike(requestDto, 1L);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("잘못된 요청입니다.");
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/like")
    ResponseEntity deleteRecipeLike(@RequestBody ReqDeleteRecipeLikeDto requestDto) throws SQLException {
        try {
            recipeService.deleteLike(requestDto, 1L);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("잘못된 요청입니다.");
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/mark")
    ResponseEntity createRecipeBookmark(@RequestBody ReqPostRecipeBookmarkDto requestDto) throws SQLException {
        recipeService.createBookmark(requestDto, 1L);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/mark")
    ResponseEntity deleteRecipeBookmark(@RequestBody ReqDeleteRecipeBookmarkDto requestDto) throws SQLException {
        recipeService.deleteBookmark(requestDto, 1L);
        return ResponseEntity.ok().build();
    }

//    @PostMapping("/comment")
//    ResponseEntity createRecipeComment(@RequestBody ReqPostRecipeCommentDto requestDto) throws SQLException {
//        recipeService.createComment(requestDto, 1L);
//        return ResponseEntity.ok().build();
//    }
//
//    @PutMapping("/comment")
//    ResponseEntity updateRecipeComment(@RequestBody ReqPutRecipeCommentDto requestDto) throws SQLException {
//        recipeService.updateComment(requestDto);
//        return ResponseEntity.ok().build();
//    }
//
//    @DeleteMapping("/comment/{recipeCommentId}")
//    ResponseEntity deleteRecipeComment(@PathVariable Long recipeCommentId) throws SQLException {
//        recipeService.deleteComment(recipeCommentId);
//        return ResponseEntity.ok().build();
//    }

    @GetMapping("")
    ResponseEntity searchRecipe(@RequestParam(required = false) String keyword,@RequestParam(required = false) String ingredients, Pageable pageable) throws SQLException {
        log.info(keyword);
        log.info(ingredients);
        if (!StringUtil.isNullOrEmpty(keyword))
            return ResponseEntity.ok(recipeService.searchRecipeByKeyword(pageable, keyword));
        else
            return ResponseEntity.ok(recipeService.searchRecipeByIngredients(pageable, ingredients));
    }

    @GetMapping("/best")
    ResponseEntity getBestRecipes(Pageable pageable) throws SQLException {
        return ResponseEntity.ok(recipeService.getBestRecipes(pageable));
    }

}
