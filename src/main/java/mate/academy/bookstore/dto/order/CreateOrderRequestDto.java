package mate.academy.bookstore.dto.order;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateOrderRequestDto(
        @NotBlank(message = "Shipping address cannot be null or empty")
        @Size(min = 10, max = 100, message =
                "Shipping address must be between 10 and 100 characters")
        String shippingAddress) {
}
