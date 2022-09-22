package com.ssafy.letcipe.api.dto.cart;

import com.ssafy.letcipe.domain.cart.Cart;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
public class ResGetCartDto {
    private ArrayList<ResGetCartItemDto> cartItems;

    public ResGetCartDto(List<Cart> cartList) {
        cartItems = new ArrayList<>();
        for(Cart cart: cartList) {
            cartItems.add(new ResGetCartItemDto(cart));
        }
    }
}
