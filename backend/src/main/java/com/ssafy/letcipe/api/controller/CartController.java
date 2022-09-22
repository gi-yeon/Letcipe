package com.ssafy.letcipe.api.controller;

import com.ssafy.letcipe.api.dto.cart.ReqDeleteCartDto;
import com.ssafy.letcipe.api.dto.cart.ReqPatchCartDto;
import com.ssafy.letcipe.api.dto.cart.ReqPostCartDto;
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
    public ResponseEntity deleteCart(@RequestBody ReqDeleteCartDto requestDto, HttpServletRequest request) {
        Long userId = jwtService.getUserId(request);
        cartService.deleteCart(requestDto, userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("cart")
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
}

