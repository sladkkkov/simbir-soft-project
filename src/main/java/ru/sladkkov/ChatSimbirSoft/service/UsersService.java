package ru.sladkkov.ChatSimbirSoft.service;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sladkkov.ChatSimbirSoft.domain.Users;
import ru.sladkkov.ChatSimbirSoft.repository.UserRepo;

import java.util.List;

@Service
@Log4j
public class UsersService   {

    private final UserRepo userRepo;

    @Autowired
    public UsersService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }


    public List<Users> getAll() {
        List<Users> usersList = userRepo.findAll();
        log.info("IN getAll " + usersList.size() + " users found");
        return usersList;
    }

    public Users findByUserLogin(String login) {
        Users users =  userRepo.findByUserLogin(login);
        log.info("IN findByUserLogin user found: " + users.getUserLogin()+ ", " + users.getUserName());
        return users;
    }


    public Users getById(Long id) {
        Users users = userRepo.getById(id);

        log.info("IN getById user found: " + users.getUserLogin()+ ", " + users.getUserName());
        return users;
    }

    public void delete(Long id) {
        userRepo.deleteById(id);
        log.info("IN delete user found and successful deleted: ");

    }
}
