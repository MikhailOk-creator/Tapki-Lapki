package ru.mirea.tapki_lapki.business_object.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.mirea.tapki_lapki.business_object.user.Role;
import ru.mirea.tapki_lapki.business_object.user.User;
import ru.mirea.tapki_lapki.business_object.user.UserRepo;

import java.util.Set;

/**
 * Service for super admin
 *
 * Super admin have more rights than ordinary admin.
 * This service contains methods for super admin.
 * Super admin can add new super admin.
 */
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

    /**
     * Method for add new super admin
     *
     * All data for new super admin is in .env file.
     * If data in .env file is null or empty method will return false.
     * If you start project not from docker, you need to add data in application.yml file.
     * @return - true if super admin was added, false if super admin wasn't added
     */
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
