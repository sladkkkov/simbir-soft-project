package ru.sladkkov.ChatSimbirSoft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sladkkov.ChatSimbirSoft.domain.Room;
import ru.sladkkov.ChatSimbirSoft.dto.response.RoomDto;
import ru.sladkkov.ChatSimbirSoft.exception.RoomAlreadyCreatedException;
import ru.sladkkov.ChatSimbirSoft.exception.RoomNotFoundException;
import ru.sladkkov.ChatSimbirSoft.service.RoomService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/chat")
public class RoomController {
    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("all-room")
    public ResponseEntity getAllRoom() {
        List<Room> roomList = null;
        try {
            roomList = roomService.getAllRoom();
        } catch (RoomNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Непонятная ошибка");
        }
        List<RoomDto> roomDtoList = new ArrayList<>();
        for (Room room : roomList) {
            roomDtoList.add(RoomDto.fromRoom(room));
        }
        return ResponseEntity.ok(roomDtoList);
    }

    @GetMapping("/room")
    public ResponseEntity getRoomById(@RequestParam Long id) {
        Room room;
        try {
            room = roomService.getRoomById(id);
        } catch (RoomNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Непонятная ошибка");
        }
        return ResponseEntity.ok(RoomDto.fromRoom(room));
    }

    @PostMapping("/rooms/create")
    public ResponseEntity createRoom(@RequestBody RoomDto roomDto) {
        try {
            roomService.createRoom(roomDto.toRoom());
            return ResponseEntity.ok("Комната успешно создана");
        } catch (RoomAlreadyCreatedException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла непонятная ошибка");
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity deleteRoomById(@RequestParam Long id) {
        try {
            roomService.deleteRoom(id);
            return ResponseEntity.ok("Пользователь успешно удалён");
        } catch (RoomNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла непонятная ошибка");
        }
    }
}
