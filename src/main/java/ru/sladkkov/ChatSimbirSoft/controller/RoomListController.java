package ru.sladkkov.ChatSimbirSoft.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sladkkov.ChatSimbirSoft.exception.LogicException;
import ru.sladkkov.ChatSimbirSoft.exception.RoomListNotFoundException;
import ru.sladkkov.ChatSimbirSoft.exception.UserBannedException;
import ru.sladkkov.ChatSimbirSoft.service.RoomListService;

@RestController
@RequestMapping("/chat/roomlist")
public class RoomListController {

    private final RoomListService roomListService;

    public RoomListController(RoomListService roomListService) {
        this.roomListService = roomListService;
    }


    @GetMapping("get-all")
    public ResponseEntity getAllRoom() throws RoomListNotFoundException {
        return ResponseEntity.ok(roomListService.getAllRoomList());
    }

    @GetMapping("/get/{roomId}")
    public ResponseEntity getRoomById(@PathVariable Long roomId) throws RoomListNotFoundException {
        return ResponseEntity.ok(roomListService.getRoomListByUserId(roomId));
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity getAllRoomById(@PathVariable Long id) throws RoomListNotFoundException {
        return ResponseEntity.ok(roomListService.getRoomListByUserId(id));
    }
    @GetMapping("/join/{roomId}")
    public ResponseEntity joinRoom(@PathVariable Long roomId) throws  LogicException, UserBannedException {
        return roomListService.createRoomList(roomId);
    }
    @DeleteMapping("/delete/{roomId}")
    public ResponseEntity deleteRoom(@PathVariable Long roomId) throws RoomListNotFoundException {
                roomListService.deleteRoomList(roomId);
        return ResponseEntity.ok("Комната успешно удалена");
    }

}

