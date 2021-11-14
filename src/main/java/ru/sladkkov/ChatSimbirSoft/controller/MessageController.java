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
    public ResponseEntity getAllMessage() {
        try {
            return ResponseEntity.ok(messageService.getAllMessage());
        } catch (MessageNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Непонятная ошибка");
        }
    }

    @GetMapping("/get")
    public ResponseEntity getMessageById(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(messageService.getMessageById(id));
        } catch (MessageNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Непонятная ошибка");
        }
    }

    @DeleteMapping("/remove")
    public ResponseEntity deleteById(@PathVariable Long  messageId, @PathVariable Long userId) {
        try {
            messageService.deleteById(messageId,userId);
            return ResponseEntity.ok("Сообщение успешно удаленно");
        } catch (MessageNotFoundException | NoAccessException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Непонятная ошибка");
        }
    }

    @PostMapping("/send")
    public ResponseEntity createMessage(@RequestBody MessageDto messageDto, @PathVariable Long room_id, Long user_id) {
        try {
            messageService.createMessage(messageDto);
            return ResponseEntity.ok("Сообщение успешно создано");
        } catch (MessageAlreadyCreatedException | UserBannedException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Непонятная ошибка");
        }
    }
    @GetMapping("/get-all/roomId")
    public ResponseEntity getAllMessageByRoom(@PathVariable Long roomId) {
        try {
            return ResponseEntity.ok(messageService.getAllMessageByRoomId(roomId));
        } catch (RoomNotFoundException | MessageNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body("Непонятная ошибка");
        }
    }
}
