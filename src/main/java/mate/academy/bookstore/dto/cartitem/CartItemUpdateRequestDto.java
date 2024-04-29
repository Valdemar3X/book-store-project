package mate.academy.bookstore.dto.cartitem;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class CartItemUpdateRequestDto {
    @Min(0)
    private int quantity;
}

