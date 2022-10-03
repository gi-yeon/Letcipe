package com.ssafy.letcipe.api.controller;

import com.ssafy.letcipe.api.dto.recipeListItem.ReqPostRecipeListItemDto;
import com.ssafy.letcipe.api.dto.recipeList.ReqCreateRecipeListDto;
import com.ssafy.letcipe.api.dto.recipeListItem.ReqDeleteRecipeListItemDto;
import com.ssafy.letcipe.api.dto.recipeList.ReqUpdateRecipeListDto;
import com.ssafy.letcipe.api.service.JwtService;
import com.ssafy.letcipe.api.service.RecipeListService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/recipelist")
@RequiredArgsConstructor
public class RecipeListController {

    private final RecipeListService recipeListService;
    private final JwtService jwtService;

    @PostMapping("")
    public ResponseEntity<?> createRecipeList(@RequestBody ReqCreateRecipeListDto reqCreateRecipeListDto, HttpServletRequest request) {
        Long userId = jwtService.getUserId(request);
        recipeListService.createRecipeList(userId, reqCreateRecipeListDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{recipe_list_id}")
    public ResponseEntity<?> getRecipeList(@PathVariable("recipe_list_id") Long recipeListId, HttpServletRequest request) {
        Long userId;
        try {
            userId = jwtService.getUserId(request);
        }catch (Exception e){
            userId = -1L;
        }
        return ResponseEntity.ok(recipeListService.getRecipeList(userId, recipeListId));
    }

    @PutMapping("/{recipe_list_id}")
    public ResponseEntity<?> updateRecipeList(@RequestBody ReqUpdateRecipeListDto reqUpdateRecipeListDto, @PathVariable("recipe_list_id") Long recipeListId, HttpServletRequest request) {
        Long userId = jwtService.getUserId(request);
        recipeListService.updateRecipeList(userId, reqUpdateRecipeListDto, recipeListId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{recipe_list_id}")
    public ResponseEntity<?> deleteRecipeList(@PathVariable("recipe_list_id") Long recipeListId, HttpServletRequest request) {
        Long userId = jwtService.getUserId(request);
        recipeListService.deleteRecipeList(userId, recipeListId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/bookmark/{recipe_list_id}")
    public ResponseEntity<?> createRecipeListBookmark(@PathVariable("recipe_list_id") Long recipeListId, HttpServletRequest request) {
        Long userId = jwtService.getUserId(request);
        recipeListService.createRecipeListBookmark(userId, recipeListId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/bookmark/{recipe_list_id}")
    public ResponseEntity<?> deleteRecipeListBookmark(@PathVariable("recipe_list_id") Long recipeListId, HttpServletRequest request) {
        Long userId = jwtService.getUserId(request);
        recipeListService.deleteRecipeListBookmark(userId, recipeListId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/recipe")
    public ResponseEntity<?> addRecipeListIte(@RequestBody ReqPostRecipeListItemDto reqPostRecipeListItemDto, HttpServletRequest request) {
        Long userId = jwtService.getUserId(request);
        recipeListService.addRecipeListItem(userId, reqPostRecipeListItemDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/recipe")
    public ResponseEntity<?> deleteRecipeListItem(@RequestBody ReqDeleteRecipeListItemDto reqDeleteRecipeListItemDto, HttpServletRequest request) {
        Long userId = jwtService.getUserId(request);
        recipeListService.deleteRecipeListItem(userId, reqDeleteRecipeListItemDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("")
    public ResponseEntity<?> searchRecipeList(@RequestParam String keyword, Pageable pageable) {
        return ResponseEntity.ok(recipeListService.searchRecipeList(pageable, keyword));
    }

    @GetMapping("/totalNum")
    public ResponseEntity<Integer> totalNumRecipeList(@RequestParam String keyword) {
        return ResponseEntity.ok(recipeListService.totalNumRecipeList(keyword));
    }
}
