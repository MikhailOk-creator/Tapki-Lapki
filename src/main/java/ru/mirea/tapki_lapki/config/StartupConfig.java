package ru.mirea.tapki_lapki.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import ru.mirea.tapki_lapki.business_object.admin.AdminService;
import ru.mirea.tapki_lapki.business_object.employee.Job;
import ru.mirea.tapki_lapki.business_object.employee.JobRepo;
import ru.mirea.tapki_lapki.business_object.user.Role;
import ru.mirea.tapki_lapki.business_object.user.User;
import ru.mirea.tapki_lapki.business_object.user.UserRepo;
import ru.mirea.tapki_lapki.business_object.admin.SuperAdminService;

import java.util.List;
import java.util.Set;

import static ru.mirea.tapki_lapki.business_object.user.Role.SUPER_ADMIN;

/**
 * Class for creating admin and job System Administrator on startup
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class StartupConfig implements ApplicationRunner {
    private final UserRepo userRepo;
    private final JobRepo jobRepo;
    private final SuperAdminService superAdminService;
    private final AdminService adminService;

    /**
     * Method for creating admin and job System Administrator on startup
     * First, it checks if there is an admin in the database. If there is, it does nothing. If there is not, it creates one. Gets data from .env file.
     * Then it checks if there is a job System Administrator in the database. If there is, it does nothing. If there is not, it creates one.
     * @param args
     * @throws Exception
     */
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

        List<Job> JobsInDB = jobRepo.findAll();
        Job job = null;
        if (JobsInDB.size() >= 0) {
            log.info("Founded jobs in Database");
            for (Job j : JobsInDB) {
                if (j.getFunction().equals("System Administrator")) {
                    log.info("Job System Administrator already exists");
                    job = j;
                    break;
                }
            }
        }
        if (job == null) {
            log.warn("Job System Administrator not found. Creating...");
            try {
                adminService.addJob("System Administrator");
                log.info("Job System Administrator created");
            } catch (Exception e) {
                log.error("Job System Administrator not created");
            }
        }
    }
}
