package ru.mirea.tapki_lapki.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import ru.mirea.tapki_lapki.entity.User;
import ru.mirea.tapki_lapki.service.AdminService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final AdminService adminService;

    @QueryMapping
    public Iterable<User> users() {
        return adminService.allUsers();
    }
}
