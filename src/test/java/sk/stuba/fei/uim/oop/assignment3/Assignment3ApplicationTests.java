package sk.stuba.fei.uim.oop.assignment3;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import sk.stuba.fei.uim.oop.assignment3.product.web.ProductController;
import sk.stuba.fei.uim.oop.assignment3.cart.web.ShoppingCartController;

import javax.print.attribute.standard.Media;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class Assignment3ApplicationTests {

    @Autowired
    ProductController productController;

    @Autowired
    ShoppingCartController shoppingCartController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void contextLoads() {
        assert Objects.nonNull(productController);
        assert Objects.nonNull(shoppingCartController);
    }

    @Test
    void testAddProduct() throws Exception {
        addProduct("name", "description", "unit", 1L);
    }

    @Test
    void testAddProduct201Response() throws Exception {
        addProduct("name", "description", "unit", 1L, status().isCreated());
    }

    @Test
    void testGetAllProduct() throws Exception {
        MvcResult result = mockMvc.perform(get("/product")
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();
        addProduct("name", "description", "unit", 1L);
        addProduct("name2", "description2", "unit2", 2L);
        mockMvc.perform(get("/product")
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andDo(mvcResult -> {
            var list = stringToObject(mvcResult, ArrayList.class);
            assert list.size() == 2 + stringToObject(result, ArrayList.class).size();
        });
    }

    @Test
    void testGetProductById() throws Exception {
        TestProductResponse product = addProduct("name", "description", "unit", 1L);
        mockMvc.perform(get("/product/" + product.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andDo(mvcResult -> {
            TestProductResponse productToControl = stringToObject(mvcResult, TestProductResponse.class);
            assert Objects.equals(productToControl.getId(), product.getId());
        });
    }

    @Test
    void testGetMissingProductById() throws Exception {
        TestProductResponse product = addProduct("name", "description", "unit", 1L);
        mockMvc.perform(get("/product/" + (product.getId() + 1))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateProduct() throws Exception {
        TestProductResponse product = addProduct("name", "description", "unit", 1L);
        TestProductRequest update1 = new TestProductRequest();
        update1.name = "updated name";
        TestProductRequest update2 = new TestProductRequest();
        update2.description = "updated description";
        mockMvc.perform(put("/product/" + product.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectToString(update1)))
                .andExpect(status().isOk()).andDo(mvcResult -> {
            TestProductResponse response = stringToObject(mvcResult, TestProductResponse.class);
            assert Objects.equals(response.getName(), update1.getName());
            assert Objects.equals(response.getDescription(), product.getDescription());
        });
        mockMvc.perform(put("/product/" + product.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectToString(update2)))
                .andExpect(status().isOk()).andDo(mvcResult -> {
            TestProductResponse response = stringToObject(mvcResult, TestProductResponse.class);
            assert Objects.equals(response.getName(), update1.getName());
            assert Objects.equals(response.getDescription(), update2.getDescription());
        });
    }

    @Test
    void testUpdateMissingProduct() throws Exception {
        TestProductResponse product = addProduct("name", "description", "unit", 1L);
        mockMvc.perform(put("/product/" + (product.getId() + 1))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectToString(new TestProductRequest())))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteProduct() throws Exception {
        TestProductResponse product = addProduct("name", "description", "unit", 1L);
        mockMvc.perform(delete("/product/" + product.getId()))
                .andExpect(status().isOk());
        mockMvc.perform(get("/product/" + product.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteMissingProduct() throws Exception {
        TestProductResponse product = addProduct("name", "description", "unit", 1L);
        mockMvc.perform(delete("/product/" + (product.getId() + 1)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetProductAmount() throws Exception {
        TestProductResponse product = addProduct("name", "description", "unit", 1L);
        mockMvc.perform(get("/product/" + product.getId() + "/amount")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(mvcResult -> {
                    Amount response = stringToObject(mvcResult, Amount.class);
                    assert Objects.equals(response.getAmount(), product.getAmount());
                });
    }

    @Test
    void testGetMissingProductAmount() throws Exception {
        TestProductResponse product = addProduct("name", "description", "unit", 1L);
        mockMvc.perform(get("/product/" + (product.getId() + 1) + "/amount")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testIncrementProductAmount() throws Exception {
        TestProductResponse product = addProduct("name", "description", "unit", 1L);
        Amount request = new Amount();
        request.setAmount(10);
        mockMvc.perform(post("/product/" + product.getId() + "/amount")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectToString(request)))
                .andExpect(status().isOk())
                .andDo(mvcResult -> {
                    Amount response = stringToObject(mvcResult, Amount.class);
                    assert Objects.equals(response.getAmount(), product.getAmount() + request.getAmount());
                });
        mockMvc.perform(get("/product/" + product.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(mvcResult -> {
                    TestProductResponse response = stringToObject(mvcResult, TestProductResponse.class);
                    assert Objects.equals(response.getAmount(), product.getAmount() + request.getAmount());
                });
    }

    @Test
    void testIncrementMissingProductAmount() throws Exception {
        TestProductResponse product = addProduct("name", "description", "unit", 1L);
        Amount request = new Amount();
        request.setAmount(10);
        mockMvc.perform(post("/product/" + (product.getId() + 1) + "/amount")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectToString(request)))
                .andExpect(status().isNotFound());
    }

    // ShoppingCart tests ==========================================================================================
    @Test
    void testAddShoppingCart() throws Exception {
        addCart(status().isCreated());
    }

    @Test
    void testGetShoppingCartById() throws Exception {
        TestCartResponse cart = addCart(status().isCreated());
        TestCartResponse cartToControl = getCart(cart.getId(), status().isOk());
        assert Objects.equals(cartToControl.getId(), cart.getId());
    }

    @Test
    void testDeleteShoppingCartById() throws Exception {
        TestCartResponse cart = addCart(status().isCreated());
        mockMvc.perform(delete("/cart/" + cart.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        mockMvc.perform(get("/cart/" + cart.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());
    }

    @Test
    void testPayForShoppingCart() throws Exception {
        TestCartResponse cart = addCart(status().isCreated());
        TestProductResponse product = addProduct("name", "description", "unit", 5L);
        TestCartEntry cartEntry = new TestCartEntry(product.getId(), product.getAmount() - 1);
        mockMvc.perform(post("/cart/add/" + cart.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectToString(cartEntry))
        ).andExpect(status().isOk()).andReturn();
        mockMvc.perform(get("/cart/" + cart.getId() + "/pay")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();
        TestCartResponse cartResponse = getCart(cart.getId(), status().isOk());
        assert cartResponse.isPayed();
    }

    TestProductResponse addProduct(String name, String description, String unit, Long amount) throws Exception {
        return addProduct(name, description, unit, amount, status().is2xxSuccessful());
    }

    TestProductResponse addProduct(String name, String description, String unit, Long amount, ResultMatcher statusMatcher) throws Exception {
        TestProductRequest product = new TestProductRequest();
        product.setName(name);
        product.setDescription(description);
        product.setUnit(unit);
        product.setAmount(amount);
        MvcResult mvcResult = mockMvc.perform(post("/product")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectToString(product))
        ).andExpect(statusMatcher)
                .andDo(mvcResult1 -> {
                    TestProductResponse productToControl = stringToObject(mvcResult1, TestProductResponse.class);
                    assert Objects.equals(product.getName(), productToControl.getName());
                    assert Objects.equals(product.getDescription(), productToControl.getDescription());
                    assert Objects.equals(product.getUnit(), productToControl.getUnit());
                    assert Objects.equals(product.getAmount(), productToControl.getAmount());
                })
                .andReturn();
        return stringToObject(mvcResult, TestProductResponse.class);
    }

    TestCartResponse addCart(ResultMatcher statusMatcher) throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/cart")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(statusMatcher)
                .andReturn();
        return stringToObject(mvcResult, TestCartResponse.class);
    }

    TestCartResponse getCart(long cartId, ResultMatcher statusMatcher) throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/cart/" + cartId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(statusMatcher).andDo(mvcResult1 -> {
            TestCartResponse cartToControl = stringToObject(mvcResult1, TestCartResponse.class);
            assert Objects.equals(cartToControl.getId(), cartId);
        }).andReturn();
        return stringToObject(mvcResult, TestCartResponse.class);
    }

    static String objectToString(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static <K> K stringToObject(MvcResult object, Class<K> objectClass) throws UnsupportedEncodingException, JsonProcessingException {
        return new ObjectMapper().readValue(object.getResponse().getContentAsString(), objectClass);
    }

    @Getter
    @Setter
    private static class Amount {
        private long amount;
    }

    @Getter
    @Setter
    private static class TestProductRequest extends Amount {
        private String name;
        private String description;
        private String unit;
        private double price;
    }

    @Getter
    @Setter
    private static class TestProductResponse extends TestProductRequest {
        private long id;
    }

    @Getter
    @Setter
    private static class TestCartResponse {
        private long id;
        private List<TestCartEntry> shoppingList;
        private boolean payed;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    private static class TestCartEntry {
        private Long productId;
        private Long amount;
    }
}
