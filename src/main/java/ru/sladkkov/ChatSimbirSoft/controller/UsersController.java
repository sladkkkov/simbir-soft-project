package ru.sladkkov.ChatSimbirSoft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.sladkkov.ChatSimbirSoft.service.UsersService;

@Controller
public class UsersController {

   private final UsersService usersService;

   @Autowired
    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }
}
