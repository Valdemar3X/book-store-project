package mate.academy.bookstore.service.impl;

import lombok.RequiredArgsConstructor;
import mate.academy.bookstore.dto.cartitem.CartItemRequestDto;
import mate.academy.bookstore.dto.cartitem.CartItemUpdateDto;
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
import mate.academy.bookstore.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final CartItemRepository cartItemRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final UserService userService;
    private final ShoppingCartMapper shoppingCartMapper;
    private final CartItemMapper cartItemMapper;

    @Override
    @Transactional
    public ShoppingCartDto getCartWithItems(String email) {
        return shoppingCartMapper.toDto(findShoppingCartByUser(email));
    }

    @Override
    @Transactional
    public void addItemToCart(String email, CartItemRequestDto requestDto) {
        CartItem cartItem = cartItemMapper.toModel(requestDto);
        ShoppingCart shoppingCart = findShoppingCartByUser(email);
        cartItem.setShoppingCart(shoppingCart);
        cartItemRepository.save(cartItem);
        shoppingCart.getCartItems().add(cartItem);
        shoppingCartRepository.save(shoppingCart);
    }

    @Override
    @Transactional
    public void updateItemInCart(
            String email,
            Long cartItemId,
            CartItemUpdateDto requestDto) {
        CartItem cartItem = findCartItemById(cartItemId);
        CartItem updatedCartItem = cartItemMapper.updateEntity(requestDto);
        cartItem.setQuantity(updatedCartItem.getQuantity());
        cartItemRepository.save(cartItem);
    }

    @Override
    @Transactional
    public void deleteItemFromCart(String email, Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }

    @Override
    @Transactional
    public ShoppingCart createShoppingCartForUser(String email) {
        ShoppingCart shoppingCart = new ShoppingCart();
        User user = userService.findUserByEmail(email);
        shoppingCart.setUser(user);
        return shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public CartItem findCartItemById(Long id) {
        return cartItemRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find item by id: " + id));
    }

    @Override
    @Transactional
    public ShoppingCart findShoppingCartByUser(String email) {
        return shoppingCartRepository.findByUser_Email(email).orElseGet(
                () -> createShoppingCartForUser(email));
    }
}
