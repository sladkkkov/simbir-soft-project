package ru.sladkkov.ChatSimbirSoft.service;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sladkkov.ChatSimbirSoft.domain.Message;
import ru.sladkkov.ChatSimbirSoft.exception.MessageAlreadyCreatedException;
import ru.sladkkov.ChatSimbirSoft.exception.MessageNotFoundException;
import ru.sladkkov.ChatSimbirSoft.repository.MessageRepo;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Log4j
@Transactional
public class MessageService {
    private final MessageRepo messageRepo;

    @Autowired
    public MessageService(MessageRepo messageRepo) {
        this.messageRepo = messageRepo;
    }

    public List<Message> getAllMessage() throws MessageNotFoundException {
        List<Message> messageList = messageRepo.findAll();
        if (messageList.size() == 0) {
            log.error("IN getAllMessage messages not found");
            throw new MessageNotFoundException("Сообщений не найдено");
        }
        log.info("Messages successfully received");
        return messageList;
    }

    public Message getMessageById(Long id) throws MessageNotFoundException {
        Message message = messageRepo.findById(id).orElse(null);
        if (message == null) {
            log.error("IN getMessageById message not found");
            throw new MessageNotFoundException("Сообщения с таким айди не существует");
        }
        log.info("Message successfully received");
        return message;
    }

    public void deleteById(Long id) throws MessageNotFoundException {
        Message message = messageRepo.findById(id).orElse(null);
        if (message == null) {
            log.error("IN deleteById message dont found");
            throw new MessageNotFoundException("Такого сообщения не существует");
        }
        log.info("IN deleteById message deleted");
        messageRepo.deleteById(id);
    }

    public void createMessage(Message message) throws MessageAlreadyCreatedException {
            if (messageRepo.findById(message.getMessageId()).orElse(null) != null) {
            log.error("IN createMessage message already created");
            throw new MessageAlreadyCreatedException("Сообщение уже существует");
        }
        log.info("IN createMessage message created");
        messageRepo.save(message);
    }
}
