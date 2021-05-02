package sk.stuba.fei.uim.oop.assignment3.cart.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.stuba.fei.uim.oop.assignment3.cart.data.ShoppingCart;
import sk.stuba.fei.uim.oop.assignment3.cart.data.ShoppingCartRepository;
import sk.stuba.fei.uim.oop.assignment3.cart.web.bodies.CartEntry;
import sk.stuba.fei.uim.oop.assignment3.exception.IllegalOperationException;
import sk.stuba.fei.uim.oop.assignment3.exception.NotFoundException;
import sk.stuba.fei.uim.oop.assignment3.product.data.Product;
import sk.stuba.fei.uim.oop.assignment3.product.logic.IProductService;

import java.util.List;

@Service
public class ShoppingCartService implements IShoppingCartService {

    @Autowired
    private ShoppingCartRepository repository;

    @Autowired
    private IProductService productService;


    @Override
    public ShoppingCart create() {
        return this.repository.save(new ShoppingCart());
    }

    @Override
    public ShoppingCart getById(long id) throws NotFoundException {
        ShoppingCart cart = this.repository.findShoppingCartById(id);
        if (cart == null) {
            throw new NotFoundException();
        }
        return cart;
    }

    @Override
    public void delete(long id) throws NotFoundException {
        this.repository.delete(this.getById(id));
    }

    @Override
    public ShoppingCart addToCart(long id, CartEntry body) throws NotFoundException, IllegalOperationException {
        ShoppingCart cart = this.getById(id);
        if (cart.isPayed()) {
            throw new IllegalOperationException();
        }
        this.productService.removeAmount(body.getProductId(), body.getAmount());
        var existingEntry = this.findCartEntryWithProduct(cart.getShoppingList(), body.getProductId());
        if (existingEntry == null) {
            cart.getShoppingList().add(this.createCartEntry(body));
        } else {
            existingEntry.setAmount(existingEntry.getAmount() + body.getAmount());
        }
        return this.repository.save(cart);
    }

    private sk.stuba.fei.uim.oop.assignment3.cart.data.CartEntry findCartEntryWithProduct(List<sk.stuba.fei.uim.oop.assignment3.cart.data.CartEntry> entries, long productId) {
        for (var entry : entries) {
            if (entry.getProduct().getId().equals(productId)) {
                return entry;
            }
        }
        return null;
    }

    private sk.stuba.fei.uim.oop.assignment3.cart.data.CartEntry createCartEntry(CartEntry body) throws NotFoundException {
        return new sk.stuba.fei.uim.oop.assignment3.cart.data.CartEntry(this.productService.getById(body.getProductId()), body.getAmount());
    }
}
