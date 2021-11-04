package ru.sladkkov.ChatSimbirSoft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.sladkkov.ChatSimbirSoft.service.MessageService;

@Controller
public class MessageController {
    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }


}
