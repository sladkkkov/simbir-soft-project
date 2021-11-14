package ru.sladkkov.ChatSimbirSoft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sladkkov.ChatSimbirSoft.exception.*;
import ru.sladkkov.ChatSimbirSoft.service.RoomService;

@RestController
@RequestMapping("/chat/room")
public class RoomController {
    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("get-all")
    public ResponseEntity getAllRoom() {
        try {
            return ResponseEntity.ok(roomService.getAllRoom());
        } catch (RoomNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Непонятная ошибка");
        }
    }

    @GetMapping("/get")
    public ResponseEntity getRoomById(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(roomService.getRoomById(id));
        } catch (RoomNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Непонятная ошибка");
        }
    }

    @PostMapping("/create/{name}/{typeRoom}/{userId}")
    public ResponseEntity createRoom(@PathVariable String name, @PathVariable String typeRoom, @PathVariable Long userId) {
        try {
            roomService.createRoom(name, typeRoom, userId);
            return ResponseEntity.ok("Комната успешно создана");
        } catch (RoomAlreadyCreatedException | UserBannedException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла непонятная ошибка");
        }
    }

    @DeleteMapping("/remove/{roomId}/{userId}")
    public ResponseEntity deleteRoomById(@PathVariable Long roomId, @PathVariable Long userId) {
        try {
            roomService.deleteRoom(roomId, userId);
            return ResponseEntity.ok("Комната успешно удалён");
        } catch (RoomNotFoundException | NoAccessException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла непонятная ошибка");
        }
    }

    @PutMapping("/rename")
    public ResponseEntity renameRoom(@RequestParam Long roomId, @RequestParam Long userId, @RequestParam String name) {
        try {
            roomService.renameRoom(roomId, userId, name);
            return ResponseEntity.ok("Комната переименована");
        } catch (RoomNotFoundException | LogicException | NoAccessException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла непонятная ошибка");
        }
    }
}
