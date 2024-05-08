package mate.academy.bookstore.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "shopping_carts")
@SQLDelete(sql = "UPDATE shopping_cart SET is_deleted = TRUE WHERE id=?")
@Where(clause = "is_deleted=FALSE")
@Data
public class ShoppingCart {
    @Id
    private Long id;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "shoppingCart")
    private Set<CartItem> cartItems = new HashSet<>();

    @Column(name = "is_deleted")
    private boolean isDeleted;
}
