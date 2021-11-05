package ru.sladkkov.ChatSimbirSoft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sladkkov.ChatSimbirSoft.domain.Users;
import ru.sladkkov.ChatSimbirSoft.dto.response.UsersDto;
import ru.sladkkov.ChatSimbirSoft.exception.UserAlreadyCreatedException;
import ru.sladkkov.ChatSimbirSoft.exception.UserNotFoundException;
import ru.sladkkov.ChatSimbirSoft.service.UsersService;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("rawtypes")
@RestController
@RequestMapping("chat")
public class UsersController {

    private final UsersService usersService;

    @Autowired
    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/users")
    public ResponseEntity getUserById(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(UsersDto.fromUser(usersService.getById(id)));
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла непонятная ошибка");
        }
    }

    @GetMapping("/all-users")
    public ResponseEntity getAllUser() {
        List<Users> usersList = null;
        try {
            usersList = usersService.getAll();
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Непонятная ошибка");
        }
        List<UsersDto> usersDtoList = new ArrayList<>();
        for (Users users : usersList) {
            usersDtoList.add(UsersDto.fromUser(users));
        }
        return ResponseEntity.ok(usersDtoList);
    }

    @DeleteMapping("/users/delete")
    public ResponseEntity delete(@RequestParam Long id) {
        try {
            usersService.deleteUser(id);
            return ResponseEntity.ok("Пользователь успешно удалён");
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла непонятная ошибка");
        }
    }

    @PostMapping("/users/create")
    public ResponseEntity createUser(@RequestBody UsersDto usersDto) {
        try {
            usersService.createUsers(usersDto.toUser());
            return ResponseEntity.ok("Пользователь успешно создан");
        } catch (UserAlreadyCreatedException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла непонятная ошибка");
        }
    }
}
