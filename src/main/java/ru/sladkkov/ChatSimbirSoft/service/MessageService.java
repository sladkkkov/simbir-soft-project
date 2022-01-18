package ru.sladkkov.ChatSimbirSoft.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.sladkkov.ChatSimbirSoft.domain.Message;
import ru.sladkkov.ChatSimbirSoft.domain.Status;
import ru.sladkkov.ChatSimbirSoft.domain.Users;
import ru.sladkkov.ChatSimbirSoft.dto.request.MessageRequestDto;
import ru.sladkkov.ChatSimbirSoft.dto.response.MessageDto;
import ru.sladkkov.ChatSimbirSoft.exception.*;
import ru.sladkkov.ChatSimbirSoft.mapper.MessageMapper;
import ru.sladkkov.ChatSimbirSoft.repository.MessageRepo;
import ru.sladkkov.ChatSimbirSoft.repository.RoomListRepo;
import ru.sladkkov.ChatSimbirSoft.repository.RoomRepo;
import ru.sladkkov.ChatSimbirSoft.repository.UserRepo;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;

@Service
@Transactional
@Log4j2
public class MessageService {
    private final MessageRepo messageRepo;
    private final RoomRepo roomRepo;
    private final RoomListRepo roomListRepo;
    private final UserRepo userRepo;

    public MessageService(MessageRepo messageRepo, RoomRepo roomRepo, RoomListRepo roomListRepo, UserRepo userRepo) {
        this.messageRepo = messageRepo;
        this.roomRepo = roomRepo;
        this.roomListRepo = roomListRepo;
        this.userRepo = userRepo;
    }

    /**
     * Метод получения всех сообщений по id.
     * Доступно для USER, MODERATOR, ADMIN, BLOCKED USER.
     */
    public List<MessageRequestDto> getAllMessage() throws MessageNotFoundException {
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
    public MessageRequestDto getMessageById(Long id) throws MessageNotFoundException {
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
    public void deleteById(Long messageId) throws MessageNotFoundException, NoAccessException {
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
     *
     * @return
     */
    public ResponseEntity createMessage(MessageDto messageDto) throws UserBannedException, LogicException {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Users user = userRepo.findByUserLogin(userDetails.getUsername()).orElse(null);
        user.setMessageList(null);
        messageDto.setUsers(user);

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        if (user.getStatus().equals(Status.BANNED)) {
            log.error("IN createMessage user blocked");
            throw new UserBannedException("Пользователь заблокирован на сайте");
        }
        Message message = MessageMapper.INSTANCE.toEntity(messageDto);
        message.setMessageText(messageDto.getMessageText());
        message.setRoom(messageDto.getRoom());
        message.setMessageTime(timestamp);
        messageRepo.save(message);
        log.info("IN createMessage message created");
        return ResponseEntity.ok("Сообщение успешно создано");
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
