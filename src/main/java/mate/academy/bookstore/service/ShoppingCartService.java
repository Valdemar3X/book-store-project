package mate.academy.bookstore.service;

import mate.academy.bookstore.dto.cartitem.CartItemRequestDto;
import mate.academy.bookstore.dto.cartitem.CartItemUpdateRequestDto;
import mate.academy.bookstore.dto.shopingcart.ShoppingCartDto;
import mate.academy.bookstore.model.User;

public interface ShoppingCartService {

    ShoppingCartDto getShoppingCart(User user);

    void addItemToCart(User user, CartItemRequestDto requestDto);

    void updateItem(
            User user,
            Long id,
            CartItemUpdateRequestDto requestDto);

    void deleteItem(Long id);

}
