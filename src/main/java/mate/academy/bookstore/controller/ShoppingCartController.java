package mate.academy.bookstore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mate.academy.bookstore.dto.cartitem.CartItemRequestDto;
import mate.academy.bookstore.dto.cartitem.CartItemUpdateRequestDto;
import mate.academy.bookstore.dto.shopingcart.ShoppingCartDto;
import mate.academy.bookstore.model.User;
import mate.academy.bookstore.service.ShoppingCartService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Cart management", description = "Endpoints for mapping shopping cart")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/cart")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    @Operation(summary = "get a user cart",
            description = "Retrieve user's shopping cart")
    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public ShoppingCartDto findUserCart(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return shoppingCartService.getShoppingCart(user);
    }

    @Operation(summary = "add book to cart",
            description = "Add book to the shopping cart")
    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public void addItemToCart(
            Authentication authentication,
            @RequestBody @Valid CartItemRequestDto requestDto) {
        User user = (User) authentication.getPrincipal();
        shoppingCartService.addItemToCart(user, requestDto);
    }

    @Operation(summary = "Update a book quantity in cart",
            description = "Update quantity of a book in the shopping cart")
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/cart-items/{cartItemId}")
    public void updateItemInCart(
            Authentication authentication,
            @PathVariable Long cartItemId,
            @RequestBody @Valid CartItemUpdateRequestDto requestDto) {
        User user = (User) authentication.getPrincipal();
        shoppingCartService.updateItem(
                        user,
                        cartItemId,
                        requestDto);
    }

    @Operation(summary = "Delete a book from cart",
            description = "Remove a book from the shopping cart")
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/cart-items/{cartItemId}")
    public void removeItemFromCart(
            Authentication authentication,
            @PathVariable Long cartItemId) {
        User user = (User) authentication.getPrincipal();
        shoppingCartService.deleteItem(
                        cartItemId);
    }
}
