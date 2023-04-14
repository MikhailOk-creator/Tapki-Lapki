package ru.mirea.tapki_lapki.business_object.user;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
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

    @MutationMapping
    public User createUser (@Argument String username,
                            @Argument String password,
                            @Argument String email,
                            @Argument String role) {
        return userService.registerUser(username, password, email, role);
    }
}
