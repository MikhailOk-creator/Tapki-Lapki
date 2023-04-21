package ru.mirea.tapki_lapki.business_object.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import ru.mirea.tapki_lapki.business_object.employee.Employee;
import ru.mirea.tapki_lapki.business_object.employee.Job;
import ru.mirea.tapki_lapki.business_object.order.Status;
import ru.mirea.tapki_lapki.business_object.user.Role;
import ru.mirea.tapki_lapki.business_object.user.User;

/**
 * Controller for admin
 *
 * This controller use admins and super admins.
 */
@Controller
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    /**
     * Method for get all users
     * @return
     */
    @QueryMapping
    public Iterable<User> users() {
        return adminService.allUsers();
    }

    /**
     * Method for get all employees
     * @see AdminService#addUserEmployee(String, String, String, String, String, String, String, Long, Double)
     * @param username username of new employee. Not null
     * @param password password of new employee. Password can be null only if User already exist
     * @param email email of new employee. Email can be null only if User's email contains a domain "@tapkil.ru"
     * @param role role of new user. Parameter not null
     * @param firstName first name of new employee. Parameter not null
     * @param lastName last name of new employee. Parameter not null
     * @param middleName middle name of new employee. Parameter not null
     * @param job_id job id of new employee. Parameter not null
     * @param salary salary of new employee. Parameter not null
     * @return result of method addUserEmployee
     */
    @MutationMapping("createEmployee")
    public Employee addUserEmployee(@Argument String username,
                                    @Argument String password,
                                    @Argument String email,
                                    @Argument String role,
                                    @Argument String firstName,
                                    @Argument String lastName,
                                    @Argument String middleName,
                                    @Argument Long job_id,
                                    @Argument Double salary) {
        return adminService.addUserEmployee(username, password, email, role, firstName, lastName, middleName, job_id, salary);
    }

    /**
     * Method for create new job
     * @param function function of new job
     * @return new job object
     */
    @MutationMapping()
    public Job createJob(@Argument String function) {
        return adminService.addJob(function);
    }

    /**
     * Method for change status of order
     * @param orderId id of order
     * @param status new status of order
     */
    @MutationMapping("changeStatusOfOrder")
    public void changeStatusOfOrder(Long orderId, Status status) {
        adminService.changeStatusOfOrder(orderId, status);
    }
}
