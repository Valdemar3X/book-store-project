package mate.academy.bookstore.service;

import mate.academy.bookstore.dto.cartitem.CartItemRequestDto;
import mate.academy.bookstore.dto.cartitem.CartItemUpdateRequestDto;
import mate.academy.bookstore.dto.shopingcart.ShoppingCartDto;

public interface ShoppingCartService {
    ShoppingCartDto getShoppingCart(Long userId);

    void addCartItemToCart(Long userId, CartItemRequestDto requestDto);

    void updateItem(
            Long userId,
            Long id,
            CartItemUpdateRequestDto requestDto);

    void deleteItem(Long id);

}
