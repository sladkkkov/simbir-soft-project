package ru.sladkkov.ChatSimbirSoft.service;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sladkkov.ChatSimbirSoft.repository.MessageRepo;

@Service
public class RoomService {

    private final MessageRepo messageRepo;

    @Autowired
    public RoomService(MessageRepo messageRepo) {
        this.messageRepo = messageRepo;
    }


}

