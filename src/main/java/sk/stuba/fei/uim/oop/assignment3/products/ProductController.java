package sk.stuba.fei.uim.oop.assignment3.products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List<Product> getAllProducts() {
        return productRepository.findAll();
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
