package mate.academy.bookstore.service.impl;

import lombok.RequiredArgsConstructor;
import mate.academy.bookstore.dto.cartitem.CartItemRequestDto;
import mate.academy.bookstore.dto.cartitem.CartItemUpdateRequestDto;
import mate.academy.bookstore.dto.shopingcart.ShoppingCartDto;
import mate.academy.bookstore.exception.EntityNotFoundException;
import mate.academy.bookstore.mapper.CartItemMapper;
import mate.academy.bookstore.mapper.ShoppingCartMapper;
import mate.academy.bookstore.model.CartItem;
import mate.academy.bookstore.model.ShoppingCart;
import mate.academy.bookstore.model.User;
import mate.academy.bookstore.repository.cartitem.CartItemRepository;
import mate.academy.bookstore.repository.shopingcart.ShoppingCartRepository;
import mate.academy.bookstore.service.ShoppingCartService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final CartItemRepository cartItemRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    private final CartItemMapper cartItemMapper;

    @Override
    public ShoppingCartDto getShoppingCart(User user) {
        ShoppingCart shoppingCart = shoppingCartRepository.findShoppingCartByUser(user)
                .orElseThrow(() -> new EntityNotFoundException(
                        "can't find shopping cart by user: " + user));
        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    @Transactional
    public void addItemToCart(User user, CartItemRequestDto requestDto) {
        CartItem cartItem = cartItemMapper.toModel(requestDto);
        ShoppingCart shoppingCart = shoppingCartRepository.findShoppingCartByUser(user)
                .orElseThrow(() -> new EntityNotFoundException(
                        "can't find shopping cart by user: " + user));
        cartItem.setShoppingCart(shoppingCart);
        cartItemRepository.save(cartItem);
        shoppingCart.getCartItems().add(cartItem);
        shoppingCartRepository.save(shoppingCart);
    }

    @Override
    @Transactional
    public void updateItem(
            User user,
            Long id,
            CartItemUpdateRequestDto requestDto) {
        ShoppingCart shoppingCart = shoppingCartRepository.findShoppingCartByUser(user)
                .orElseThrow(() -> new EntityNotFoundException(
                        "can't find shopping cart by user: " + user));
        CartItem cartItem = shoppingCart.getCartItems().stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(
                        "can't find item by id: " + id));
        cartItem.setQuantity(requestDto.getQuantity());
        cartItemRepository.save(cartItem);
    }

    @Override
    public void deleteItem(Long id) {
        cartItemRepository.deleteById(id);
    }
}
