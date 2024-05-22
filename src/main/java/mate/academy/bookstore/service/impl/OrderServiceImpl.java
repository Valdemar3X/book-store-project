package mate.academy.bookstore.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.bookstore.dto.order.CreateOrderRequestDto;
import mate.academy.bookstore.dto.order.OrderResponseDto;
import mate.academy.bookstore.dto.order.UpdateOrderDto;
import mate.academy.bookstore.dto.orderitem.OrderItemDto;
import mate.academy.bookstore.exception.EntityNotFoundException;
import mate.academy.bookstore.mapper.OrderItemMapper;
import mate.academy.bookstore.mapper.OrderMapper;
import mate.academy.bookstore.model.Order;
import mate.academy.bookstore.model.OrderItem;
import mate.academy.bookstore.model.ShoppingCart;
import mate.academy.bookstore.repository.order.OrderRepository;
import mate.academy.bookstore.repository.orderitem.OrderItemRepository;
import mate.academy.bookstore.repository.shopingcart.ShoppingCartRepository;
import mate.academy.bookstore.service.OrderService;
import mate.academy.bookstore.service.ShoppingCartService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
    private final ShoppingCartService shoppingCartService;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;
    private final Order order = new Order();

    @Override
    @Transactional
    public void createUserOrder(Long userId, CreateOrderRequestDto requestDto) {
        ShoppingCart shoppingCart = shoppingCartService.getShoppingCartModel(userId);
        if (shoppingCart.getCartItems().isEmpty()) {
            throw new EntityNotFoundException("Cart is empty for user: " + userId);
        }
        Order order = orderMapper.cartToOrder(shoppingCart, requestDto.shippingAddress());
        orderRepository.save(order);
        shoppingCart.getCartItems().clear();
        shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public List<OrderResponseDto> findAllUserOrders(String email, Pageable pageable) {
        List<Order> orders = orderRepository.findByUser_Email(email, pageable);
        return orderMapper.toDtoList(orders);
    }

    @Transactional
    @Override
    public void updateOrderStatus(Long id, UpdateOrderDto requestDto) {
        Order orderById = orderRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find order by id: " + id));
        orderById.setStatus(requestDto.status());
        orderRepository.save(orderById);
    }

    @Override
    public List<OrderItemDto> findAllItemsFromOrder(Long orderId, Pageable pageable) {
        List<OrderItem> orderItems = orderItemRepository.findAllByOrder_Id(orderId, pageable);
        return orderItemMapper.toDtoList(orderItems);
    }

    @Override
    public OrderItemDto findOrderItemByOrderId(Long orderId, Long itemId) {
        return orderItemMapper.toDto(
                orderItemRepository.findByOrder_IdAndId(orderId, itemId));
    }
}
