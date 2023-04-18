package ru.mirea.tapki_lapki.business_object.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import ru.mirea.tapki_lapki.business_object.employee.Job;
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
    public void addUserEmployee(@Argument String username,
                                @Argument String password,
                                @Argument String email,
                                @Argument String role,
                                @Argument String first_name,
                                @Argument String last_name,
                                @Argument String middle_name,
                                @Argument Long job_id,
                                @Argument Double salary) {
        adminService.addUserEmployee(username, password, email, role, first_name, last_name, middle_name, job_id, salary);
    }

    @MutationMapping()
    public Job createJob(@Argument String function) {
        return adminService.addJob(function);
    }

    @MutationMapping("changeStatusOfOrder")
    public void changeStatusOfOrder(Long orderId, Status status) {
        adminService.changeStatusOfOrder(orderId, status);
    }
}
