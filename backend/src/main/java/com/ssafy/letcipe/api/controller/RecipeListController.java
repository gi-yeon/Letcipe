package com.ssafy.letcipe.api.controller;

import com.ssafy.letcipe.api.dto.ReqCreateRecipeListDto;
import com.ssafy.letcipe.api.service.RecipeListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recipelist")
@RequiredArgsConstructor
public class RecipeListController {

    private final RecipeListService recipeListService;

//    @PostMapping("/")
//    public ResponseEntity<?> createRecipeList(@RequestBody ReqCreateRecipeListDto reqCreateRecipeListDto){
//        recipeListService.createRecipeList(reqCreateRecipeListDto);
//        return ResponseEntity.ok(null);
//    }

    @GetMapping("/{recipe_list_id}")
    public ResponseEntity<?> getRecipeList(@PathVariable("recipe_list_id") Long recipeListId){
        return ResponseEntity.ok(recipeListService.getRecipeList(recipeListId));
    }
}
