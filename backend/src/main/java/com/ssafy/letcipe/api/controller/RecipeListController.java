package com.ssafy.letcipe.api.controller;

import com.ssafy.letcipe.api.dto.recipeListItem.ReqPostRecipeListItemDto;
import com.ssafy.letcipe.api.dto.recipeList.ReqCreateRecipeListDto;
import com.ssafy.letcipe.api.dto.recipeListItem.ReqDeleteRecipeListItemDto;
import com.ssafy.letcipe.api.dto.recipeList.ReqUpdateRecipeListDto;
import com.ssafy.letcipe.api.service.RecipeListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recipelist")
@RequiredArgsConstructor
public class RecipeListController {

    private final RecipeListService recipeListService;

    @PostMapping("")
    public ResponseEntity<?> createRecipeList(String accessToken, @RequestBody ReqCreateRecipeListDto reqCreateRecipeListDto) {
        recipeListService.createRecipeList(accessToken, reqCreateRecipeListDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{recipe_list_id}")
    public ResponseEntity<?> getRecipeList(@PathVariable("recipe_list_id") Long recipeListId) {
        return ResponseEntity.ok(recipeListService.getRecipeList(recipeListId));
    }

    @PutMapping("/{recipe_list_id}")
    public ResponseEntity<?> updateRecipeList(String accessToken, @RequestBody ReqUpdateRecipeListDto reqUpdateRecipeListDto, @PathVariable("recipe_list_id") Long recipeListId) {
        recipeListService.updateRecipeList(accessToken, reqUpdateRecipeListDto, recipeListId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{recipe_list_id}")
    public ResponseEntity<?> deleteRecipeList(String accessToken, @PathVariable("recipe_list_id") Long recipeListId) {
        recipeListService.deleteRecipeList(accessToken, recipeListId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/bookmark/{recipe_list_id}")
    public ResponseEntity<?> createRecipeListBookmark(String accessToken, @PathVariable("recipe_list_id") Long recipeListId) {
        recipeListService.createRecipeListBookmark(accessToken, recipeListId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/bookmark/{recipe_list_id}")
    public ResponseEntity<?> deleteRecipeListBookmark(String accessToken, @PathVariable("recipe_list_id") Long recipeListId) {
        recipeListService.deleteRecipeListBookmark(accessToken, recipeListId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/recipe")
    public ResponseEntity<?> addRecipeListItem(String accessToken, @RequestBody ReqPostRecipeListItemDto reqPostRecipeListItemDto) {
        recipeListService.addRecipeListItem(accessToken, reqPostRecipeListItemDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/recipe")
    public ResponseEntity<?> deleteRecipeListItem(String accessToken, @RequestBody ReqDeleteRecipeListItemDto reqDeleteRecipeListItemDto) {
        recipeListService.deleteRecipeListItem(accessToken, reqDeleteRecipeListItemDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("")
    public ResponseEntity<?> searchRecipeList(@RequestParam String keyword){
        return ResponseEntity.ok(recipeListService.searchRecipeList(keyword));
    }
}
