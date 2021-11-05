package ru.sladkkov.ChatSimbirSoft.service;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sladkkov.ChatSimbirSoft.domain.Users;
import ru.sladkkov.ChatSimbirSoft.exception.UserAlreadyCreatedException;
import ru.sladkkov.ChatSimbirSoft.exception.UserNotFoundException;
import ru.sladkkov.ChatSimbirSoft.repository.UserRepo;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Log4j
@Transactional
public class UsersService {

    private final UserRepo userRepo;

    @Autowired
    public UsersService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }


    public List<Users> getAll() throws UserNotFoundException {
        List<Users> usersList = userRepo.findAll();
        if(usersList.size() == 0){
            log.error("IN getAll users not found");
            throw new UserNotFoundException("Пользователей не найдено");
        }
        log.info("IN getAll " + usersList.size() + " users found");
        return usersList;
    }

    public Users getById(Long id) throws UserNotFoundException {
        Users user = userRepo.findById(id).orElse(null);
        if (user == null) {
            log.error("IN getById user by ID: " + id + " not found");
            throw new UserNotFoundException("Пользователь с таким id не найден");
        }
        log.info("IN getById user by ID " + id + " found");
        return userRepo.getById(id);
    }

    public void deleteUser(Long id) throws UserNotFoundException {
        Users user = userRepo.findById(id).orElse(null);
        if (user == null) {
            log.error("IN deleteUser user by ID: " + id + " not found");
            throw new UserNotFoundException("Пользователь с таким id не найден");
        }
        log.info("IN deleteUser user with " + id + " found and successful deleted");
        userRepo.deleteById(id);
    }

    public void createUsers(Users user) throws UserAlreadyCreatedException {

        if (userRepo.findById(user.getUserId()).orElse(null) != null) {
            log.error("IN createUser user already created");
            throw new UserAlreadyCreatedException("Такой пользователь уже существует");
        }
        log.info("IN createUser user created");
        userRepo.save(user);
    }
}

