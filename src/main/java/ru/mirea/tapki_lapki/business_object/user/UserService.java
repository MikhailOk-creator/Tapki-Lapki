package ru.mirea.tapki_lapki.business_object.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.mirea.tapki_lapki.business_object.client.Client;
import ru.mirea.tapki_lapki.business_object.client.ClientRepo;

import java.util.Set;

/**
 * Service for User
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepo userRepo;
    private final ClientRepo clientRepo;

    /**
     * Method for getting user by id
     * @param id id of user
     * @return user
     */
    public User getUserById(Long id) {
        return userRepo.findById(id).orElse(null);
    }

    /**
     * Method for registering user
     * @param username username of user
     * @param password password of user
     * @param email email of user
     * @param role role of user
     * @return user that was registered
     */
    public User registerUser(String username, String password, String email, String role) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.setEmail(email);
        user.setRoles(Set.of(Role.CLIENT));
        user.setActive(true);
        userRepo.save(user);
        log.info("Created new user (username: {})", user.getUsername());

        Client client = new Client();
        client.setUser(userRepo.findByUsername(username));
        client.setCart(false);
        clientRepo.save(client);

        return user;
    }
}
