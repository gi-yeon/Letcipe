package com.ssafy.letcipe.api.dto.cart;

import com.ssafy.letcipe.domain.cart.Cart;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class ResGetCartDto {
    private List<ResGetCartItemDto> cartItems;

    public ResGetCartDto(List<ResGetCartItemDto> cartList) {
        cartItems = cartList;
    }
}
