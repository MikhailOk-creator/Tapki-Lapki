package ru.mirea.tapki_lapki.business_object.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
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

    public List<User> allUsers() {
        return userRepo.findAll();
    }

    public void addUserEmployee(String username, String password, String email, Role role) {
        try {
            User user = new User();

            log.info("Data in .env: " + "\n" +
                    "Username: " + username + '\n' +
                    "Email: " + email + '\n');

            if ((username != null && !username.equals(""))  &&
                    (password != null && !password.equals("")) &&
                    (email != null && !email.equals(""))) {
                user.setUsername(username);
                user.setPassword(password);
                user.setEmail(email);

                user.setActive(true);
                user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
                user.setRoles(Set.of(role));
                userRepo.save(user);
                log.info("User with username {} saved", username);
            } else {
                log.error("Main data is not full!");
            }
        } catch (Exception e) {
            log.error("Something went wrong in the process of writing to the database!");
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
