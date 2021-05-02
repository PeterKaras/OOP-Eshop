package sk.stuba.fei.uim.oop.assignment3.cart.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.stuba.fei.uim.oop.assignment3.cart.data.ShoppingCart;
import sk.stuba.fei.uim.oop.assignment3.cart.logic.IShoppingCartService;
import sk.stuba.fei.uim.oop.assignment3.cart.web.bodies.CartEntry;
import sk.stuba.fei.uim.oop.assignment3.cart.web.bodies.CartResponse;
import sk.stuba.fei.uim.oop.assignment3.product.data.Product;

import java.util.Optional;

@RestController
@RequestMapping("/cart")
public class ShoppingCartController {

    @Autowired
    private IShoppingCartService service;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CartResponse addCart() {
        return new CartResponse(this.service.create());
    }

//    @GetMapping(value = "/pay/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    public @ResponseBody ResponseEntity<Double> payCart(@PathVariable("id") Long cartId) {
//        Optional<ShoppingCart> cart = cartRepository.findById(cartId);
//        if (!cart.isPresent()) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        ShoppingCart newCart = cart.get();
//        if (newCart.isPayed()) {
//            return new ResponseEntity<>(HttpStatus.GONE);
//        }
//        return new ResponseEntity<Double>(newCart.getShoppingList().stream().mapToDouble(item -> item.getAmount() * item.getProduct().getPrice()).sum(), HttpStatus.OK);
//    }
//
//    @PostMapping(value = "/add/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    public @ResponseBody ResponseEntity<ShoppingCart> addToCart(@PathVariable("id") Long cartId, @RequestBody CartEntry cartEntry) {
//        Optional<ShoppingCart> cart = cartRepository.findById(cartId);
//        if (!cart.isPresent()) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        ShoppingCart newCart = cart.get();
//        Product product = productRepository.findProductById(cartEntry.getProductId());
//        newCart.getShoppingList().add(new sk.stuba.fei.uim.oop.assignment3.cart.data.CartEntry(product, cartEntry.getAmount()));
//        cartRepository.save(newCart);
//        return new ResponseEntity<>(newCart, HttpStatus.OK);
//    }
}
