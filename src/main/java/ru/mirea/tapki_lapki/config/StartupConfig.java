package ru.mirea.tapki_lapki.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import ru.mirea.tapki_lapki.entity.Role;
import ru.mirea.tapki_lapki.entity.User;
import ru.mirea.tapki_lapki.repo.UserRepo;
import ru.mirea.tapki_lapki.service.SuperAdminService;

import java.util.List;
import java.util.Set;

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
        for (User u : usersInDB) {
            Set<Role> roles = u.getRoles();
            for (Role r : roles) {
                if (r.getAuthority().equals("SUPER_ADMIN")) {
                    user = u;
                    break;
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
