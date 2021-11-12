package ru.sladkkov.ChatSimbirSoft.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sladkkov.ChatSimbirSoft.domain.Message;
import ru.sladkkov.ChatSimbirSoft.dto.response.MessageDto;
import ru.sladkkov.ChatSimbirSoft.exception.MessageAlreadyCreatedException;
import ru.sladkkov.ChatSimbirSoft.exception.MessageNotFoundException;
import ru.sladkkov.ChatSimbirSoft.repository.MessageRepo;
import ru.sladkkov.ChatSimbirSoft.service.mapper.MessageMapper;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@Log4j2
public class MessageService {
    private final MessageRepo messageRepo;
    @Autowired
    public MessageService(MessageRepo messageRepo) {
        this.messageRepo = messageRepo;
    }


    public List<MessageDto> getAllMessage() throws MessageNotFoundException {
        List<Message> messageList = messageRepo.findAll();
        if (messageList.size() == 0) {
            log.error("IN getAllMessage messages not found");
            throw new MessageNotFoundException("Сообщений не найдено");
        }
        log.info("Messages successfully received");
        return MessageMapper.INSTANCE.toModelList(messageList);
    }

    public MessageDto getMessageById(Long id) throws MessageNotFoundException {
        Message message = messageRepo.findById(id).orElse(null);
        if (message == null) {
            log.error("IN getMessageById message not found");
            throw new MessageNotFoundException("Сообщения с таким айди не существует");
        }
        log.info("Message successfully received");
        return MessageMapper.INSTANCE.toModel(message);
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

    public void createMessage(MessageDto messageDto) throws MessageAlreadyCreatedException {
            if (messageRepo.findById(messageDto.getMessageId()).orElse(null) != null) {
            log.error("IN createMessage message already created");
            throw new MessageAlreadyCreatedException("Сообщение уже существует");
        }
        log.info("IN createMessage message created");
        messageRepo.save(MessageMapper.INSTANCE.toEntity(messageDto));
    }
}
