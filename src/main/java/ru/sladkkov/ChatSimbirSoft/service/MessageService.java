package ru.sladkkov.ChatSimbirSoft.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ru.sladkkov.ChatSimbirSoft.domain.Message;
import ru.sladkkov.ChatSimbirSoft.dto.response.MessageDto;
import ru.sladkkov.ChatSimbirSoft.exception.*;
import ru.sladkkov.ChatSimbirSoft.repository.MessageRepo;
import ru.sladkkov.ChatSimbirSoft.repository.RoomListRepo;
import ru.sladkkov.ChatSimbirSoft.repository.RoomRepo;
import ru.sladkkov.ChatSimbirSoft.mapper.MessageMapper;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@Log4j2
public class MessageService {
    private final MessageRepo messageRepo;
    private final RoomRepo roomRepo;
    private  final RoomListRepo roomListRepo;

    public MessageService(MessageRepo messageRepo, RoomRepo roomRepo, RoomListRepo roomListRepo) {
        this.messageRepo = messageRepo;
        this.roomRepo = roomRepo;
        this.roomListRepo = roomListRepo;
    }

    /**
     * Метод получения всех сообщений по id.
     * Доступно для USER, MODERATOR, ADMIN, BLOCKED USER.
     */
    public List<MessageDto> getAllMessage() throws MessageNotFoundException {
        List<Message> messageList = messageRepo.findAll();
        if (messageList.size() == 0) {
            log.error("IN getAllMessage messages not found");
            throw new MessageNotFoundException("Сообщений не найдено");
        }
        log.info("Messages successfully received");
        return MessageMapper.INSTANCE.toModelList(messageList);
    }

    /**
     * Метод получения сообщения по id.
     */
    public MessageDto getMessageById(Long id) throws MessageNotFoundException {
        Message message = messageRepo.findById(id).orElse(null);
        if (message == null) {
            log.error("IN getMessageById message not found");
            throw new MessageNotFoundException("Сообщения с таким айди не существует");
        }
        log.info("Message successfully received");
        return MessageMapper.INSTANCE.toModel(message);
    }

    /**
     * Метод удаления сообщения.
     * Доступно для MODERATOR, ADMIN.
     */
    public void deleteById(Long messageId, Long userId) throws MessageNotFoundException, NoAccessException {
        Message message = messageRepo.findById(messageId).orElse(null);
        if (message == null) {
            log.error("IN deleteById message dont found");
            throw new MessageNotFoundException("Такого сообщения не существует");
        }
        log.info("IN deleteById message deleted");
        messageRepo.deleteById(messageId);
    }

    /**
     * Метод отправки сообщения.
     * Доступно для USER, MODERATOR, ADMIN.
     */
    public void createMessage(MessageDto messageDto) throws MessageAlreadyCreatedException, UserBannedException, LogicException {
        Message message = MessageMapper.INSTANCE.toEntity(messageDto);
        if (messageRepo.findById(messageDto.getMessageId()).orElse(null) != null) {
            log.error("IN createMessage message already created");
            throw new MessageAlreadyCreatedException("Сообщение c таким id уже существует");
        }
        if(roomListRepo.getByUserIdAndRoomRoomId(message.getUsers().getUserId(),message.getRoom().getRoomId()) == null){
            log.error("IN createMessage user dont consist of room");
            throw new LogicException("aa не добавлен в комнату");
        }
        if(!message.getUsers().getStatus().name().equals("ACTIVE")){
            log.error("IN createMessage user blocked");
            throw new UserBannedException("Пользователь заблокирован на сайте");
        }
        log.info("IN createMessage message created");
        messageRepo.save(message);
    }

    /**
     * Метод получения всех сообщений комнаты.
     * Доступно для USER, MODERATOR, ADMIN, BLOCKED USER.
     */
    public List<Message> getAllMessageByRoomId(Long roomId) throws RoomNotFoundException, MessageNotFoundException {
        if (roomRepo.findById(roomId).orElse(null) == null) {
            log.error("IN getAllMessageByRoomId room not found");
            throw new RoomNotFoundException("Комнаты с таким айди не существует");
        }
        if (messageRepo.findByRoom_RoomId(roomId) == null) {
            log.error("IN getAllMessageByRoomId message not found");
            throw new MessageNotFoundException("В этой комнате нет сообщений");
        }
        log.info("IN getAllMessageByRoomId message get");
        return messageRepo.findByRoom_RoomId(roomId);
    }
}
