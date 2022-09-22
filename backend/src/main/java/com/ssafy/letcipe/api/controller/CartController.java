package com.ssafy.letcipe.api.controller;

import com.ssafy.letcipe.api.dto.cart.ReqDeleteCartDto;
import com.ssafy.letcipe.api.dto.cart.ReqPatchCartDto;
import com.ssafy.letcipe.api.dto.cart.ReqPostCartDto;
import com.ssafy.letcipe.api.dto.cart.ReqPostCartIngredientDto;
import com.ssafy.letcipe.api.service.CartService;
import com.ssafy.letcipe.api.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final JwtService jwtService;

    private final Logger logger = LoggerFactory.getLogger(CartController.class);

    @PostMapping("cart")
    public ResponseEntity createCart(@RequestBody ReqPostCartDto requestDto, HttpServletRequest request) {
        Long userId = jwtService.getUserId(request);
        cartService.createCart(requestDto, userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("cart")
    //TODO: 추가재료도 삭제해주기
    public ResponseEntity deleteCart(@RequestBody ReqDeleteCartDto requestDto, HttpServletRequest request) {
        Long userId = jwtService.getUserId(request);
        cartService.deleteCart(requestDto, userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("cart")
    //TODO: 추가재료 수량까지 계산해서 재료도 보내주기
    public ResponseEntity readCart(HttpServletRequest request) {
        Long userId = jwtService.getUserId(request);
        return ResponseEntity.ok(cartService.getCart(userId));
    }

    @PatchMapping("cart-recipe")
    public ResponseEntity updateCartRecipe(@RequestBody ReqPatchCartDto requestDto, HttpServletRequest request){
        Long userId = jwtService.getUserId(request);
        cartService.updateCartRecipe(requestDto, userId);
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("cart-ingredient")
    public ResponseEntity createCartIngredient(@RequestBody ReqPostCartIngredientDto requestDto, HttpServletRequest request) {
        Long userId = jwtService.getUserId(request);
        cartService.createCartIngredient(requestDto, userId);
        return ResponseEntity.ok().build();
    }
}

