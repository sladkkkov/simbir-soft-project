package ru.sladkkov.ChatSimbirSoft.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sladkkov.ChatSimbirSoft.exception.RoomListNotFoundException;
import ru.sladkkov.ChatSimbirSoft.service.RoomListService;

@RestController
@RequestMapping("/chat/roomlist")
public class RoomListController {
    private final RoomListService roomListRepo;

    public RoomListController(RoomListService roomListRepo) {
        this.roomListRepo = roomListRepo;
    }


    @GetMapping("get-all")
    public ResponseEntity getAllRoom() throws RoomListNotFoundException {

        return ResponseEntity.ok(roomListRepo.getAllRoomList());
    }

    @GetMapping("/get/{userId}/{roomId}")
    public ResponseEntity getRoomById(@PathVariable Long userId, @PathVariable Long roomId) throws RoomListNotFoundException {
        return ResponseEntity.ok(roomListRepo.getRoomListByUserId(userId, roomId));
    }


    @GetMapping("/get-by-id/{id}")
    public ResponseEntity getAllRoomById(@PathVariable Long id) throws RoomListNotFoundException {
        return ResponseEntity.ok(roomListRepo.getAllRoomByUserId(id));
    }

}

