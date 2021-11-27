package ru.sladkkov.ChatSimbirSoft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sladkkov.ChatSimbirSoft.dto.response.MessageDto;
import ru.sladkkov.ChatSimbirSoft.exception.*;
import ru.sladkkov.ChatSimbirSoft.service.MessageService;

@RestController
@RequestMapping("/chat/message")
public class MessageController {
    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/get-all")
    public ResponseEntity getAllMessage() throws MessageNotFoundException {
            return ResponseEntity.ok(messageService.getAllMessage());
    }

    @GetMapping("/get")
    public ResponseEntity getMessageById(@RequestParam Long id) throws MessageNotFoundException {
            return ResponseEntity.ok(messageService.getMessageById(id));
    }

    @DeleteMapping("/remove")
    public ResponseEntity deleteById(@PathVariable Long  messageId, @PathVariable Long userId) throws MessageNotFoundException, NoAccessException {
            messageService.deleteById(messageId,userId);
            return ResponseEntity.ok("Сообщение успешно удаленно");
    }

    @PostMapping("/send")
    public ResponseEntity createMessage(@RequestBody MessageDto messageDto, @PathVariable Long room_id, Long user_id) throws MessageAlreadyCreatedException, LogicException, UserBannedException {
            messageService.createMessage(messageDto);
            return ResponseEntity.ok("Сообщение успешно создано");
    }
    @GetMapping("/get-all/roomId")
    public ResponseEntity getAllMessageByRoom(@PathVariable Long roomId) throws MessageNotFoundException, RoomNotFoundException {
            return ResponseEntity.ok(messageService.getAllMessageByRoomId(roomId));
    }
}
