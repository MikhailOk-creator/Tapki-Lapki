package ru.mirea.tapki_lapki.business_object.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import ru.mirea.tapki_lapki.business_object.order.Status;
import ru.mirea.tapki_lapki.business_object.user.Role;
import ru.mirea.tapki_lapki.business_object.user.User;

@Controller
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @QueryMapping
    public Iterable<User> users() {
        return adminService.allUsers();
    }

    @MutationMapping("createEmployee")
    public void addUserEmployee(String username, String password, String email, Role role) {
        adminService.addUserEmployee(username, password, email, role);
    }

    @MutationMapping("changeStatusOfOrder")
    public void changeStatusOfOrder(Long orderId, Status status) {
        adminService.changeStatusOfOrder(orderId, status);
    }
}
