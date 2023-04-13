package ru.mirea.tapki_lapki.business_object.user;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import ru.mirea.tapki_lapki.business_object.admin.AdminService;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @QueryMapping
    public User user(Long id) {
        return userService.getUserById(id);
    }

    @QueryMapping
    public void createUser (String username, String password, String email, String role) {
        userService.registerUser(username, password, email, role);
    }
}
