package sk.stuba.fei.uim.oop.assignment3;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import sk.stuba.fei.uim.oop.assignment3.products.ProductController;
import sk.stuba.fei.uim.oop.assignment3.shopping_cart.ShoppingCartController;
import sk.stuba.fei.uim.oop.assignment3.user.User;
import sk.stuba.fei.uim.oop.assignment3.user.UserController;

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
        createUser("Objekt" , "Objektovy");
    }

    @Test
    void testUpdateUser() throws Exception {
        User userToUpdate = createUser("Objekt", "Objektovy");
        userToUpdate.setSurname("Zmena");
        mockMvc.perform(put("/user/"+ userToUpdate.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectToString(userToUpdate))
        ).andExpect(status().isOk())
        .andDo(mvcResult -> {
            User userToControl = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), User.class);
            assert Objects.equals(userToControl.getSurname(), userToUpdate.getSurname());
            assert Objects.equals(userToControl.getName(), userToUpdate.getName());
        });
    }

    @Test
    void getUserById() throws Exception {
        User user = createUser("Objekt", "Objektovy");
        mockMvc.perform(get("/user/"+ user.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andDo(mvcResult -> {
            User userToControl = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), User.class);
            assert Objects.equals(userToControl.getId(), user.getId());
        });
    }

    @Test
    void getAll() throws Exception {
        createUser("Objekt1", "O");
        createUser("Objekt2", "O");
        mockMvc.perform(get("/user")
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andDo(mvcResult -> {
            ArrayList list = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), ArrayList.class);
            assert list.size() == 2;
        });

    }

    User createUser(String name, String surname) throws Exception {
        User user = new User();
        user.setName(name);
        user.setSurname(surname);
        MvcResult mvcResult = mockMvc.perform(post("/user/add")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectToString(user))
        ).andExpect(status().isOk())
                .andDo(mvcResult1 -> {
                    User userToControl = new ObjectMapper().readValue(mvcResult1.getResponse().getContentAsString(), User.class);
                    assert Objects.equals(userToControl.getName(), user.getName());
                    assert Objects.equals(userToControl.getSurname(), user.getSurname());
                    assert Objects.nonNull(userToControl.getId());
                }).andReturn();
        return new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), User.class);
    }


    String objectToString(Object object){
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
