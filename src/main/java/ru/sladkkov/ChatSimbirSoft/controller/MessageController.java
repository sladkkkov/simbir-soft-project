package ru.sladkkov.ChatSimbirSoft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import ru.sladkkov.ChatSimbirSoft.domain.Message;
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

    @DeleteMapping("/remove/{messageId}")
    public ResponseEntity deleteById(@PathVariable Long messageId) throws MessageNotFoundException, NoAccessException {
        messageService.deleteById(messageId);
        return ResponseEntity.ok("Сообщение успешно удаленно");
    }

    @PostMapping("/send")
    public ResponseEntity createMessage(@RequestBody MessageDto messageDto) throws MessageAlreadyCreatedException, LogicException, UserBannedException {
        return messageService.createMessage(messageDto);
    }

    @GetMapping("/get-all/{roomId}")
    public ResponseEntity getAllMessageByRoom(@PathVariable Long roomId) throws MessageNotFoundException, RoomNotFoundException {
        return ResponseEntity.ok(messageService.getAllMessageByRoomId(roomId));
    }

    @MessageMapping("chat.send")
    @SendTo("/topic/public")
    public Message sendMessage(@Payload Message message){
        return message;
    }
}
