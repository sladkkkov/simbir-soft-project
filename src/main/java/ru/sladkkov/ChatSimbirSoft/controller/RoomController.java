package ru.sladkkov.ChatSimbirSoft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAnyAuthority('USER') OR hasAnyAuthority('ADMIN')")
    public ResponseEntity getAllRoom() throws RoomNotFoundException {
        return ResponseEntity.ok(roomService.getAllRoom());
    }

    @GetMapping("/get")
    @PreAuthorize("hasAnyAuthority('USER') OR hasAnyAuthority('ADMIN')")
    public ResponseEntity getRoomById(@RequestParam Long id) throws RoomNotFoundException {
        return ResponseEntity.ok(roomService.getRoomById(id));
    }

    @PostMapping("/create/{name}/{typeRoom}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity createRoom(@PathVariable String name, @PathVariable String typeRoom) throws UserBannedException {
        roomService.createRoom(name, typeRoom);
        return ResponseEntity.ok("Комната успешно создана");
    }

    @DeleteMapping("/remove/{roomId}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity deleteRoomById(@PathVariable Long roomId) throws NoAccessException, RoomNotFoundException {
            roomService.deleteRoom(roomId);
            return ResponseEntity.ok("Комната успешно удалён");
    }

    @PutMapping("/rename")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity renameRoom(@RequestParam Long roomId,  @RequestParam String name) throws RoomNotFoundException, LogicException, NoAccessException {
            roomService.renameRoom(roomId, name);
            return ResponseEntity.ok("Комната переименована");
    }
}
