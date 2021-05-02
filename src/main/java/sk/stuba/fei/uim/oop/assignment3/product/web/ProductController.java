package sk.stuba.fei.uim.oop.assignment3.product.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import sk.stuba.fei.uim.oop.assignment3.product.data.Product;
import sk.stuba.fei.uim.oop.assignment3.product.logic.IProductService;
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
        return service.getAll().stream().map(ProductResponse::new).collect(Collectors.toList());
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Product getProduct(@PathVariable("id") Long productId) {
        return productRepository.findProductById(productId);
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

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Product addProduct(@RequestBody Product body) {
        Product product = new Product();
        product.setName(body.getName());
        product.setDescription(body.getDescription());
        product.setUnit(body.getUnit());
        product.setAmount(body.getAmount());
        productRepository.save(product);
        return product;
    }
}
