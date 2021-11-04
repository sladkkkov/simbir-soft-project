package ru.sladkkov.ChatSimbirSoft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sladkkov.ChatSimbirSoft.domain.Users;
import ru.sladkkov.ChatSimbirSoft.dto.response.UsersDto;
import ru.sladkkov.ChatSimbirSoft.service.UsersService;

@RestController
@RequestMapping("chat")
public class UsersController {

    private final UsersService usersService;

    @Autowired
    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/users/{id}")
    public UsersDto getUserById(@PathVariable(name = "id") Long id) {
        Users user = usersService.getById(id);
        return UsersDto.fromUser(user);
    }
}
