package ru.mirea.tapki_lapki.business_object.user;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import ru.mirea.tapki_lapki.business_object.admin.AdminService;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final AdminService adminService;

    @QueryMapping
    public Iterable<User> users() {
        return adminService.allUsers();
    }
}
