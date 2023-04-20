package ru.mirea.tapki_lapki.business_object.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.mirea.tapki_lapki.business_object.employee.Employee;
import ru.mirea.tapki_lapki.business_object.employee.EmployeeRepo;
import ru.mirea.tapki_lapki.business_object.employee.Job;
import ru.mirea.tapki_lapki.business_object.employee.JobRepo;
import ru.mirea.tapki_lapki.business_object.order.Order;
import ru.mirea.tapki_lapki.business_object.order.OrderRepo;
import ru.mirea.tapki_lapki.business_object.order.Status;
import ru.mirea.tapki_lapki.business_object.user.Role;
import ru.mirea.tapki_lapki.business_object.user.User;
import ru.mirea.tapki_lapki.business_object.user.UserRepo;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminService {
    private final UserRepo userRepo;
    private final OrderRepo orderRepo;
    private final EmployeeRepo employeeRepo;
    private final JobRepo jobRepo;

    public List<User> allUsers() {
        return userRepo.findAllByOrderByIdAsc();
    }

    public Employee addUserEmployee(String username, String password, String email, String role,
                                String firstName, String lastName, String middleName, Long jobId, Double salary) {
        try {
            User user = new User();
            log.info("Trying to find user by username {}", username);
            if (userRepo.findByUsername(username) == null) {

                if ((username != null && !username.equals(""))  &&
                        (password != null && !password.equals("")) &&
                        (email != null && !email.equals(""))) {
                    user.setUsername(username);
                    user.setPassword(password);
                    user.setEmail(email);

                    user.setActive(true);
                    user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
                    switch (role) {
                        case "manager":
                            user.setRoles(Set.of(Role.MANAGER));
                            break;
                        case "admin":
                            user.setRoles(Set.of(Role.ADMIN));
                            break;
                    }
                    userRepo.save(user);
                } else {
                    log.error("Main data is not full!");
                    return null;
                }
            } else {
                log.info("Founded user in Database");
                user = userRepo.findByUsername(username);
                switch (role) {
                    case "manager":
                        user.setRoles(Set.of(Role.MANAGER));
                        break;
                    case "admin":
                        user.setRoles(Set.of(Role.ADMIN));
                        break;
                    default:
                        break;
                }
                userRepo.save(user);
            }

            log.info("Start to create employee");
            Employee new_employee = new Employee();
            new_employee.setUser(user);
            new_employee.setFirstName(firstName);
            new_employee.setLastName(lastName);
            new_employee.setMiddleName(middleName);
            // log.info("Set info about name");
            new_employee.setJob(jobRepo.findById(jobId).orElse(null));
            // log.info("Set info about job");
            new_employee.setSalary(salary);
            // log.info("Set info about salary");

            if (user.getEmail().contains("@tapkil.ru")) {
                new_employee.setEmail(user.getEmail());
            } else {
                new_employee.setEmail(email);
            }
            // log.info("Set email: {}", new_employee.getEmail());

            new_employee.setHire_date(new java.sql.Date(System.currentTimeMillis()));
            // log.info("Set info about hire date");
            employeeRepo.save(new_employee);

            log.info("Employee with username {} saved", username);
            return new_employee;
        } catch (Exception e) {
            log.error("Something went wrong in the process of writing to the database!");
            log.error(e.getMessage());
            return null;
        }
    }

    public Job addJob(String function) {
        try {
            Job new_job = new Job();
            new_job.setFunction(function);
            jobRepo.save(new_job);
            log.info("Job with function {} saved", function);
            return new_job;
        } catch (Exception e) {
            log.error("Something went wrong in the process of writing to the database!");
            return null;
        }
    }

    public void changeStatusOfOrder(Long orderId, Status status) {
        try {
            Order order = orderRepo.getReferenceById(orderId);
            order.setStatusOfOrder(status);
            orderRepo.save(order);
            log.info("The order status with id {} has changed successfully", order.getId());
        } catch (Exception e) {
            log.error("Error when changing the order status!");
        }
    }

    public void changeUserActivity(Long id) {
        try {
            User user = userRepo.findByUsername(userRepo.getReferenceById(id).getUsername());
            user.setActive(!user.isActive());
            userRepo.save(user);
            log.info("User's (id: {}, username: {}) activity status has been changed ({})",
                    user.getId(), user.getUsername(), user.isActive());
        } catch (Exception e) {
            log.error("Wrong input data");
        }
    }
}
