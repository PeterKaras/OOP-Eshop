package sk.stuba.fei.uim.oop.assignment3;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import sk.stuba.fei.uim.oop.assignment3.product.data.Product;
import sk.stuba.fei.uim.oop.assignment3.product.web.ProductController;
import sk.stuba.fei.uim.oop.assignment3.cart.web.ShoppingCartController;
import sk.stuba.fei.uim.oop.assignment3.user.User;
import sk.stuba.fei.uim.oop.assignment3.user.UserController;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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
    UserController userController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void contextLoads() {
        assert Objects.nonNull(productController);
        assert Objects.nonNull(shoppingCartController);
        assert Objects.nonNull(userController);
    }

    @Test
    void testCreateUser() throws Exception {
        addUser("Objekt", "Objektovy");
    }

    @Test
    void testUpdateUser() throws Exception {
        User userToUpdate = addUser("Objekt", "Objektovy");
        userToUpdate.setSurname("Zmena");
        mockMvc.perform(put("/user/" + userToUpdate.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectToString(userToUpdate))
        ).andExpect(status().isOk())
                .andDo(mvcResult -> {
                    User userToControl = stringToObject(mvcResult, User.class);
                    assert Objects.equals(userToControl.getSurname(), userToUpdate.getSurname());
                    assert Objects.equals(userToControl.getName(), userToUpdate.getName());
                });
    }

    @Test
    void testGetUserById() throws Exception {
        User user = addUser("Objekt", "Objektovy");
        mockMvc.perform(get("/user/" + user.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andDo(mvcResult -> {
            User userToControl = stringToObject(mvcResult, User.class);
            assert Objects.equals(userToControl.getId(), user.getId());
        });
    }

    @Test
    void testGetAllUsers() throws Exception {
        addUser("Objekt1", "O");
        addUser("Objekt2", "O");
        mockMvc.perform(get("/user")
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andDo(mvcResult -> {
            ArrayList list = stringToObject(mvcResult, ArrayList.class);
            assert list.size() == 2;
        });
    }

    @Test
    void testAddProduct() throws Exception {
        addProduct("name","description", "unit", 1L);
    }

    @Test
    void testGetAllProduct() throws Exception {
        addProduct("name","description", "unit", 1L);
        addProduct("name2","description2", "unit2", 2L);
        mockMvc.perform(get("/product")
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andDo(mvcResult -> {
            ArrayList list = stringToObject(mvcResult, ArrayList.class);
            assert list.size() == 2;
        });
    }

    @Test
    void testGetProductById() throws Exception {
        Product product = addProduct("name", "description", "unit", 1L);
        mockMvc.perform(get("/product/" + product.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andDo(mvcResult -> {
            Product productToControl = stringToObject(mvcResult, Product.class);
            assert Objects.equals(productToControl.getId(), product.getId());
        });
    }

    User addUser(String name, String surname) throws Exception {
        User user = new User();
        user.setName(name);
        user.setSurname(surname);
        MvcResult mvcResult = mockMvc.perform(post("/user/add")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectToString(user))
        ).andExpect(status().isOk())
                .andDo(mvcResult1 -> {
                    User userToControl = stringToObject(mvcResult1, User.class);
                    assert Objects.equals(userToControl.getName(), user.getName());
                    assert Objects.equals(userToControl.getSurname(), user.getSurname());
                    assert Objects.nonNull(userToControl.getId());
                }).andReturn();
        return stringToObject(mvcResult, User.class);
    }

    Product addProduct(String name, String description, String unit, Long amount) throws Exception {
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setUnit(unit);
        product.setAmount(amount);
        MvcResult mvcResult = mockMvc.perform(post("/product")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectToString(product))
        ).andExpect(status().isOk())
                .andDo(mvcResult1 -> {
                    Product productToControl = stringToObject(mvcResult1, Product.class);
                    assert Objects.equals(product.getName(), productToControl.getName());
                    assert Objects.equals(product.getDescription(), productToControl.getDescription());
                    assert Objects.equals(product.getUnit(), productToControl.getUnit());
                    assert Objects.equals(product.getAmount(), productToControl.getAmount());
                })
                .andReturn();
        return stringToObject(mvcResult, Product.class);
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

}
