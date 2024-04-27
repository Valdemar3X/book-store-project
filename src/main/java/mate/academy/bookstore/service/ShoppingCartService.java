package mate.academy.bookstore.service;

import mate.academy.bookstore.dto.cartitem.CartItemRequestDto;
import mate.academy.bookstore.dto.cartitem.CartItemUpdateDto;
import mate.academy.bookstore.dto.shopingcart.ShoppingCartDto;
import mate.academy.bookstore.model.CartItem;
import mate.academy.bookstore.model.ShoppingCart;

public interface ShoppingCartService {
    ShoppingCartDto getCartWithItems(String email);

    void addItemToCart(String email, CartItemRequestDto requestDto);

    void updateItemInCart(String email, Long cartItemId, CartItemUpdateDto requestDto);

    void deleteItemFromCart(String email, Long cartItemId);

    ShoppingCart createShoppingCartForUser(String email);

    CartItem findCartItemById(Long id);

    ShoppingCart findShoppingCartByUser(String email);
}
