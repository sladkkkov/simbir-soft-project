package ru.sladkkov.ChatSimbirSoft.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sladkkov.ChatSimbirSoft.repository.MessageRepo;

@Service
public class MessageService {
   private final MessageRepo messageRepo;

   @Autowired
    public MessageService(MessageRepo messageRepo) {
        this.messageRepo = messageRepo;
    }



}
