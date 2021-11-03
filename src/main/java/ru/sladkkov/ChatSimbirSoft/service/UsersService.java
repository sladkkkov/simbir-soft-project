package ru.sladkkov.ChatSimbirSoft.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sladkkov.ChatSimbirSoft.repository.UserRepo;

@Service
public class UsersService {
 private final UserRepo userRepo;

    @Autowired
    public UsersService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

}
