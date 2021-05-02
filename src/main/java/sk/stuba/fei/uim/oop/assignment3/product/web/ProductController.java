package sk.stuba.fei.uim.oop.assignment3.product.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.stuba.fei.uim.oop.assignment3.product.data.Product;
import sk.stuba.fei.uim.oop.assignment3.product.logic.IProductService;
import sk.stuba.fei.uim.oop.assignment3.product.web.bodies.ProductRequest;
import sk.stuba.fei.uim.oop.assignment3.product.web.bodies.ProductResponse;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private IProductService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProductResponse> getAllProducts() {
        return this.service.getAll().stream().map(ProductResponse::new).collect(Collectors.toList());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductResponse> addProduct(@RequestBody ProductRequest body) {
        return new ResponseEntity<>(new ProductResponse(this.service.create(body)), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductResponse> getProduct(@PathVariable("id") Long productId) {
        Product product = this.service.getById(productId);
        if (product == null) {
            return notFound();
        }
        return ok(product);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Product updateDescription(@PathVariable("id") Long productId, @RequestBody String description) {
        Product product = productRepository.findProductById(productId);
        product.setDescription(description);
        productRepository.save(product);
        return product;
    }

    @GetMapping(value = "/amount/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Long getAmount(@PathVariable("id") Long productId) {
        return productRepository.findProductById(productId).getAmount();
    }

    @PostMapping(value = "/amount/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Long addAmount(@PathVariable("id") Long productId, @RequestBody Long amount) {
        Product product = productRepository.findProductById(productId);
        product.setAmount(product.getAmount() + amount);
        productRepository.save(product);
        return product.getAmount();
    }

    private ResponseEntity<ProductResponse> notFound() {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<ProductResponse> ok(Product product) {
        return new ResponseEntity<>(new ProductResponse(product), HttpStatus.NOT_FOUND);
    }
}
