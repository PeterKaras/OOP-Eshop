package sk.stuba.fei.uim.oop.assignment3.shopping_cart;
import lombok.AllArgsConstructor;
import lombok.Data;
import sk.stuba.fei.uim.oop.assignment3.products.Product;

import javax.persistence.*;

@Data
@Entity
public class CartEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Product product;

    private Long amount;

    public CartEntry() {

    }

    public CartEntry(Product product, Long amount) {
        this.product = product;
        this.amount = amount;
    }
}
