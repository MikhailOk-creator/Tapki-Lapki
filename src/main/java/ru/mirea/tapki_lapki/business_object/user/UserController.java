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

    /**
     * Method for getting user by id
     * @param id
     * @return
     */
    @QueryMapping
    public User user(Long id) {
        return userService.getUserById(id);
    }

    /**
     * Method for registering user
     * @param username username of user
     * @param password password of user
     * @param email email of user
     * @param role role of user
     * @return user that was registered
     */
    @MutationMapping
    public User createUser (@Argument String username,
                            @Argument String password,
                            @Argument String email,
                            @Argument String role) {
        return userService.registerUser(username, password, email, role);
    }
}
