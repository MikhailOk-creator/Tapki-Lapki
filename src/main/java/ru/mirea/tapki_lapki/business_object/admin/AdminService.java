package ru.mirea.tapki_lapki.business_object.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mirea.tapki_lapki.business_object.user.User;
import ru.mirea.tapki_lapki.business_object.user.UserRepo;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepo userRepo;

    public List<User> allUsers() {
        return userRepo.findAll();
    }
}
