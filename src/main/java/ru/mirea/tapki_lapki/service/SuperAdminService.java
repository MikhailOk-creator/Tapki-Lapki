package ru.mirea.tapki_lapki.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.mirea.tapki_lapki.entity.Role;
import ru.mirea.tapki_lapki.entity.User;
import ru.mirea.tapki_lapki.repo.UserRepo;

import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class SuperAdminService {
    @Value("${admin.username}")
    private String username;
    @Value("${admin.password}")
    private String password;
    @Value("${admin.email}")
    private String email;
    @Value("${admin.real_name}")
    private String real_name;
    @Value("${admin.second_name}")
    private String second_name;
    private final UserRepo userRepo;

    public boolean addSuperAdmin() {
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
                user.setRoles(Set.of(Role.SUPER_ADMIN));
                userRepo.save(user);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }
}
