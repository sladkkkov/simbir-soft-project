package ru.sladkkov.ChatSimbirSoft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sladkkov.ChatSimbirSoft.domain.Message;
import ru.sladkkov.ChatSimbirSoft.dto.response.MessageDto;
import ru.sladkkov.ChatSimbirSoft.exception.MessageAlreadyCreatedException;
import ru.sladkkov.ChatSimbirSoft.exception.MessageNotFoundException;
import ru.sladkkov.ChatSimbirSoft.service.MessageService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/chat")
public class MessageController {
    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/all-message")
    public ResponseEntity getAllMessage() {
        List<Message> messageList;
        List<MessageDto> messageDtoList;
        try {
            messageList = messageService.getAllMessage();
            messageDtoList = new ArrayList<>();
            for (Message message : messageList) {
                messageDtoList.add(MessageDto.fromMessage(message));
            }
        } catch (MessageNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Непонятная ошибка");
        }

        return ResponseEntity.ok(messageDtoList);
    }

    @GetMapping("/message")
    public ResponseEntity getMessageById(@RequestParam Long id) {
        Message message;
        try {
            message = messageService.getMessageById(id);
        } catch (MessageNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Непонятная ошибка");
        }
        return ResponseEntity.ok(MessageDto.fromMessage(message));
    }

    @DeleteMapping("/message/delete")
    public ResponseEntity deleteById(@RequestParam Long id) {
        Message message = null;
        try {
            messageService.deleteById(id);
        } catch (MessageNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Непонятная ошибка");
        }
        return ResponseEntity.ok("Сообщение успешно удаленно");
    }

    @PostMapping("/message/create")
    public ResponseEntity createMessage(@RequestBody MessageDto messageDto) {
        try {
            messageService.createMessage(messageDto.toMessage());
            return ResponseEntity.ok("Сообщение успешно создано");
        } catch (MessageAlreadyCreatedException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Непонятная ошибка");
        }

    }
}
