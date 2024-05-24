package mate.academy.bookstore.dto.order;

import jakarta.validation.constraints.NotNull;
import mate.academy.bookstore.model.Order;

public record UpdateOrderDto(@NotNull Order.Status status) {
}
