package com.ssafy.letcipe.api.service;

import com.ssafy.letcipe.api.dto.cart.ReqDeleteCartDto;
import com.ssafy.letcipe.api.dto.cart.ReqPostCartDto;
import com.ssafy.letcipe.domain.cart.Cart;
import com.ssafy.letcipe.domain.cart.CartRepository;
import com.ssafy.letcipe.domain.recipe.Recipe;
import com.ssafy.letcipe.domain.recipe.RecipeRepository;
import com.ssafy.letcipe.domain.user.User;
import com.ssafy.letcipe.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;
    private final CartRepository cartRepository;

    @Transactional
    public void createCart(ReqPostCartDto requestDto, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NullPointerException("유저를 찾을 수 없습니다."));
        Recipe recipe = recipeRepository.findById(requestDto.getRecipeId())
                .orElseThrow(() -> new NullPointerException("레시피를 찾을 수 없습니다."));;

        cartRepository.save(new Cart(user, recipe, 1));
    }
}
