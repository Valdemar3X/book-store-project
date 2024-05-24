package mate.academy.bookstore.mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import mate.academy.bookstore.config.MapperConfig;
import mate.academy.bookstore.dto.order.OrderResponseDto;
import mate.academy.bookstore.dto.order.UpdateOrderDto;
import mate.academy.bookstore.model.CartItem;
import mate.academy.bookstore.model.Order;
import mate.academy.bookstore.model.ShoppingCart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(config = MapperConfig.class, uses = OrderItemMapper.class)
public interface OrderMapper {
    Order updateEntity(UpdateOrderDto request);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "orderItems", target = "orderItems")
    OrderResponseDto toDto(Order order);

    List<OrderResponseDto> toDtoList(List<Order> orders);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "total", source = "cart.cartItems", qualifiedByName = "total")
    @Mapping(target = "orderItems", source = "cart.cartItems")
    Order cartToOrder(ShoppingCart cart, String shippingAddress);

    @Named("total")
    default BigDecimal getTotal(Set<CartItem> cartItems) {
        return cartItems.stream()
                .map(i -> i.getBook().getPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
