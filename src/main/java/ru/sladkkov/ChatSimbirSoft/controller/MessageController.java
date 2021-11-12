package ru.sladkkov.ChatSimbirSoft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sladkkov.ChatSimbirSoft.dto.response.MessageDto;
import ru.sladkkov.ChatSimbirSoft.exception.MessageAlreadyCreatedException;
import ru.sladkkov.ChatSimbirSoft.exception.MessageNotFoundException;
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
    public ResponseEntity deleteById(@RequestParam Long id) {
        try {
            messageService.deleteById(id);
            return ResponseEntity.ok("Сообщение успешно удаленно");
        } catch (MessageNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Непонятная ошибка");
        }
    }

    @PostMapping("/create")
    public ResponseEntity createMessage(@RequestBody MessageDto messageDto) {
        try {
            messageService.createMessage(messageDto);
            return ResponseEntity.ok("Сообщение успешно создано");
        } catch (MessageAlreadyCreatedException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Непонятная ошибка");
        }
    }
}
