package ru.sladkkov.ChatSimbirSoft.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sladkkov.ChatSimbirSoft.exception.*;
import ru.sladkkov.ChatSimbirSoft.service.RoomListService;

@RestController
@RequestMapping("/chat/roomlist")
public class RoomListController {
    private final RoomListService roomListRepo;

    public RoomListController(RoomListService roomListRepo) {
        this.roomListRepo = roomListRepo;
    }


    @GetMapping("get-all")
    public ResponseEntity getAllRoom() {
        try {
            return ResponseEntity.ok(roomListRepo.getAllRoomList());
        } catch (RoomListNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Непонятная ошибка");
        }
    }

    @GetMapping("/get/{userId}/{roomId}")
    public ResponseEntity getRoomById(@PathVariable Long userId,@PathVariable Long roomId) {
        try {
            return ResponseEntity.ok(roomListRepo.getRoomListByUserId(userId,roomId));
        } catch (RoomListNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Непонятная ошибка");
        }
    }


    @GetMapping("/get-by-id/{id}")
    public ResponseEntity getAllRoomById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(roomListRepo.getAllRoomByUserId(id));
        } catch (RoomListNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Непонятная ошибка");
        }
    }

}

