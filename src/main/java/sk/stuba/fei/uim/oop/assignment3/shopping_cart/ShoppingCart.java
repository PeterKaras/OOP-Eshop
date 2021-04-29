package sk.stuba.fei.uim.oop.assignment3.shopping_cart;

import lombok.Data;
import sk.stuba.fei.uim.oop.assignment3.user.User;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private User user;

    @OneToMany(orphanRemoval = true)
    private List<CartEntry> shoppingList;

    private boolean payed;
}
