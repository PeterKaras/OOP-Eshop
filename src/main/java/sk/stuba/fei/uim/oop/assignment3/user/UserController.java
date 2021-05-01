package sk.stuba.fei.uim.oop.assignment3.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody User getUser(@PathVariable("id") Long userId) {
        return userRepository.findUserById(userId);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody User getUser(@PathVariable("id") Long userId, @RequestBody User body) {
        User updatedUser = userRepository.findUserById(userId);
        if(Objects.nonNull(body.getName()))
            updatedUser.setName(body.getName());
        if(Objects.nonNull(body.getSurname()))
            updatedUser.setSurname(body.getSurname());
        userRepository.save(updatedUser);
        return updatedUser;
    }

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody User addUser(@RequestBody User body) {
        User newUser = new User();
        newUser.setName(body.getName());
        newUser.setSurname(body.getSurname());
        userRepository.save(newUser);
        return newUser;
    }
}
