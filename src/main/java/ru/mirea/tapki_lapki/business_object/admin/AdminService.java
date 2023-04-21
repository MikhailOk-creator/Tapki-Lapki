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

/**
 * Service for admin
 *
 * This service contains methods for admin.
 * Admin can see all users, add employee, add job, change status of order, change user's activity.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AdminService {
    private final UserRepo userRepo;
    private final OrderRepo orderRepo;
    private final EmployeeRepo employeeRepo;
    private final JobRepo jobRepo;

    /**
     * Method for get all users
     *
     * @return list of all users
     */
    public List<User> allUsers() {
        return userRepo.findAllByOrderByIdAsc();
    }

    /**
     * Method for create new employee
     *
     * First of all method check if user with this username already exist in database.
     * If user already exist method will use this user and change his role.
     * After that method will create new employee and save it in database.
     *
     * @param username username of new employee
     * @param password password of new employee. Password can be null only if User already exist
     * @param email email of new employee. Email can be null only if User's email contains a domain "@tapkil.ru"
     * @param role role of new user. Parameter not null
     * @param firstName first name of new employee. Parameter not null
     * @param lastName last name of new employee. Parameter not null
     * @param middleName middle name of new employee. Parameter not null
     * @param jobId job id of new employee. Parameter not null. Job is not equal to User's role
     * @param salary salary of new employee. Parameter not null
     * @return new employee object or null if something went wrong
     */
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

    /**
     * Method for create new job
     *
     * @param function function of new job
     * @return new job object or null if something went wrong
     */
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

    /**
     * Method for change status of order
     *
     * @param orderId id of order
     * @param status new status of order
     */
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

    /**
     * Method for change activity status of user
     *
     * @param id id of user
     */
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
