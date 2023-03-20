package ru.mirea.tapki_lapki.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mirea.tapki_lapki.entity.User;
import ru.mirea.tapki_lapki.repo.UserRepo;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepo userRepo;

    public List<User> allUsers() {
        return userRepo.findAll();
    }
}
