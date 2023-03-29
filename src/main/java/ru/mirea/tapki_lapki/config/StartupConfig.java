package ru.mirea.tapki_lapki.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import ru.mirea.tapki_lapki.business_object.user.Role;
import ru.mirea.tapki_lapki.business_object.user.User;
import ru.mirea.tapki_lapki.business_object.user.UserRepo;
import ru.mirea.tapki_lapki.business_object.admin.SuperAdminService;

import java.util.List;
import java.util.Set;

import static ru.mirea.tapki_lapki.business_object.user.Role.SUPER_ADMIN;

@Component
@Slf4j
@RequiredArgsConstructor
public class StartupConfig implements ApplicationRunner {
    private final UserRepo userRepo;
    private final SuperAdminService superAdminService;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<User> usersInDB = userRepo.findAll();
        User user = null;
        if (usersInDB.size() >= 0) {
            log.info("Founded users in Database");
            for (User u : usersInDB) {
                Set<Role> roles = u.getRoles();
                for (Role role : roles) {
                    if(role.equals(SUPER_ADMIN)) {
                        user = u;
                        break;
                    }
                }
            }
        }

        if (user == null) {
            log.warn("Admin not found. Creating...");
            if(superAdminService.addSuperAdmin()) {
                log.info("Admin created");
            } else {
                log.error("Admin not created. Check .env file");
            }
        } else {
            log.info("Admin already exists");
        }
    }
}
