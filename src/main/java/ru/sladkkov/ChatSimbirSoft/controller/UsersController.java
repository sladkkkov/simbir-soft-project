package ru.sladkkov.ChatSimbirSoft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.sladkkov.ChatSimbirSoft.dto.response.UsersDto;
import ru.sladkkov.ChatSimbirSoft.exception.LogicException;
import ru.sladkkov.ChatSimbirSoft.exception.NoAccessException;
import ru.sladkkov.ChatSimbirSoft.exception.UserAlreadyCreatedException;
import ru.sladkkov.ChatSimbirSoft.exception.UserNotFoundException;
import ru.sladkkov.ChatSimbirSoft.service.UsersService;

@RestController
@RequestMapping("chat/user")
public class UsersController {

    private final UsersService usersService;

    @Autowired
    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @PreAuthorize("hasAnyAuthority('USER') OR hasAnyAuthority('ADMIN')")
    @GetMapping("/get")
    public ResponseEntity getUserById(@RequestParam Long id) throws UserNotFoundException {
        return ResponseEntity.ok(usersService.getById(id));
    }

    @PreAuthorize("hasAnyAuthority('USER') OR hasAnyAuthority('ADMIN')")
    @GetMapping("/get-all")
    public ResponseEntity getAllUser() throws UserNotFoundException {
        return ResponseEntity.ok(usersService.getAll());
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @DeleteMapping("/remove")
    public ResponseEntity delete(@RequestParam Long id) throws UserNotFoundException {
        usersService.deleteUser(id);
        return ResponseEntity.ok("Пользователь успешно удалён");
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity createUser(@RequestBody UsersDto usersDto) throws UserAlreadyCreatedException {
        usersService.createUsers(usersDto);
        return ResponseEntity.ok("Пользователь успешно создан");
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PutMapping("/ban/{userId}")
    public ResponseEntity blockUser(@PathVariable Long userId) throws UserNotFoundException,  LogicException {
        usersService.blockUser(userId);
        return ResponseEntity.ok("Пользователь успешно забанен");
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PutMapping("/unban/{userId}")
    public ResponseEntity unblockUser(@PathVariable Long userId) throws UserNotFoundException,  LogicException {
        usersService.unblockUser(userId);
        return ResponseEntity.ok("Пользователь успешно разбанен");
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PutMapping("/set-moderator/{userId}/{roomId}")
    public ResponseEntity setModerator(@PathVariable Long userId, @PathVariable Long roomId) throws UserNotFoundException {
        usersService.setModerator(userId,  roomId);
        return ResponseEntity.ok("Пользователь успешно получил права модератора");
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PutMapping("/delete-moderator/{userId}/{roomId}")
    public ResponseEntity deleteModerator(@PathVariable Long userId, @PathVariable Long roomId) throws UserNotFoundException {
        usersService.deleteModerator(userId,  roomId);
        return ResponseEntity.ok("Пользователь успешно утратил права модератора ");
    }
}
