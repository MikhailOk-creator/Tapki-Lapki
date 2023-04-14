package ru.mirea.tapki_lapki.business_object.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepo userRepo;

    public User getUserById(Long id) {
        return userRepo.findById(id).orElse(null);
    }

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
        return user;
    }
}
