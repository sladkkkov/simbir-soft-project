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

    @PreAuthorize("hasAnyAuthority('user') OR hasAnyAuthority('admin')")
    @GetMapping("/get")
    public ResponseEntity getUserById(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(usersService.getById(id));
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла непонятная ошибка");
        }
    }
    @PreAuthorize("hasAnyAuthority('user') OR hasAnyAuthority('admin')")
    @GetMapping("/get-all")
    public ResponseEntity getAllUser() {
        try {
            return ResponseEntity.ok(usersService.getAll());
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Непонятная ошибка");
        }
    }
    @PreAuthorize("hasAnyAuthority('admin')")
    @DeleteMapping("/remove")
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

    @PreAuthorize("hasAnyAuthority('admin')")
    @PostMapping("/create")
    public ResponseEntity createUser(@RequestBody UsersDto usersDto) {
        try {
            usersService.createUsers(usersDto);
            return ResponseEntity.ok("Пользователь успешно создан");
        } catch (UserAlreadyCreatedException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла непонятная ошибка");
        }
    }

    @PreAuthorize("hasAnyAuthority('admin')")
    @PutMapping("/ban/{userId}/{moderatorOrAdministratorId}/{roomId}")
    public ResponseEntity blockUser(@PathVariable Long userId, @PathVariable Long moderatorOrAdministratorId, @PathVariable Long roomId) {
        try {
            usersService.blockUser(userId, moderatorOrAdministratorId, roomId);
            return ResponseEntity.ok("Пользователь успешно забанен");
        } catch (UserNotFoundException | NoAccessException | LogicException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла непонятная ошибка");
        }
    }

    @PreAuthorize("hasAnyAuthority('admin')")
    @PutMapping("/unban/{userId}/{roomId}/{moderatorOrAdministratorId}")
    public ResponseEntity unblockUser(@PathVariable Long userId, @PathVariable Long roomId, @PathVariable Long moderatorOrAdministratorId) {
        try {
            usersService.unblockUser(userId, moderatorOrAdministratorId, roomId);
            return ResponseEntity.ok("Пользователь успешно разбанен");
        } catch (UserNotFoundException | NoAccessException | LogicException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла непонятная ошибка");
        }
    }

    @PreAuthorize("hasAnyAuthority('admin')")
    @PutMapping("/set-moderator/{userId}/{roomId}/{administratorId}")
    public ResponseEntity setModerator(@PathVariable Long userId, @PathVariable Long roomId, @PathVariable Long administratorId) {
        try {
            usersService.setModerator(userId, administratorId, roomId);
            return ResponseEntity.ok("Пользователь успешно получил права модератора");
        } catch (UserNotFoundException | NoAccessException | LogicException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyAuthority('admin')")
    @PutMapping("/delete-moderator/{userId}/{roomId}/{administratorId}")
    public ResponseEntity deleteModerator(@PathVariable Long userId, @PathVariable Long roomId, @PathVariable Long administratorId) {
        try {
            usersService.deleteModerator(userId, administratorId, roomId);
            return ResponseEntity.ok("Пользователь успешно утратил права модератора ");
        } catch (UserNotFoundException | NoAccessException | LogicException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла непонятная ошибка");
        }
    }
}
