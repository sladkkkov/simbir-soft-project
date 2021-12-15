package ru.sladkkov.ChatSimbirSoft.service;

import lombok.extern.log4j.Log4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.sladkkov.ChatSimbirSoft.domain.*;
import ru.sladkkov.ChatSimbirSoft.dto.response.RoomDto;
import ru.sladkkov.ChatSimbirSoft.dto.response.RoomListDto;
import ru.sladkkov.ChatSimbirSoft.exception.*;
import ru.sladkkov.ChatSimbirSoft.mapper.RoomListMapper;
import ru.sladkkov.ChatSimbirSoft.mapper.RoomMapper;
import ru.sladkkov.ChatSimbirSoft.mapper.UsersMapper;
import ru.sladkkov.ChatSimbirSoft.repository.RoomListRepo;
import ru.sladkkov.ChatSimbirSoft.repository.RoomRepo;
import ru.sladkkov.ChatSimbirSoft.repository.UserRepo;

import javax.persistence.GeneratedValue;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Log4j
@Transactional
public class RoomService {

    private final RoomRepo roomRepo;
    private final UserRepo userRepo;
    private final RoomListRepo roomListRepo;

    public RoomService(RoomRepo roomRepo, UserRepo userRepo, RoomListRepo roomListRepo) {
        this.roomRepo = roomRepo;
        this.userRepo = userRepo;
        this.roomListRepo = roomListRepo;
    }

    /**
     * Метод получения всех комнат.
     */
    public List<RoomDto> getAllRoom() throws RoomNotFoundException {
        if (roomRepo.findAll().size() == 0) {
            log.error("IN getAllRoom rooms not found");
            throw new RoomNotFoundException("Комнат не найдено");
        }
        log.info("IN getAllRoom rooms successfully founded");
        return RoomMapper.INSTANCE.toModelList(roomRepo.findAll());
    }

    /**
     * Метод получения комнаты по id.
     */
    public RoomDto getRoomById(Long id) throws RoomNotFoundException {
        Room room = roomRepo.findById(id).orElse(null);
        if (room == null) {
            log.error("IN getRoom room not found");
            throw new RoomNotFoundException("Комната не найдена");
        }
        log.info("IN getRoom room successfully founded");
        return RoomMapper.INSTANCE.toModel(room);
    }

    /**
     * Метод создания комнаты.
     * Доступен всем кроме заблокированного пользователя
     */
    public void createRoom(String name, String typeRoom) throws UserBannedException {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Users user = userRepo.findByUserLogin(userDetails.getUsername()).orElse(null);
        if (user.getStatus().equals(Status.BANNED)) {
            log.error("IN createRoom create room failed, user don't Active");
            throw new UserBannedException("Пользователь забанен на сайте");
        }
        if (typeRoom == "c") {
            RoomDto roomDto = new RoomDto(user.getUserId(), name, "private");
            log.info("IN createRoom room private room created");
            roomRepo.save(RoomMapper.INSTANCE.toEntity(roomDto));


        }

        Room room = new Room(user.getUserId(), name, "public");
/*
        RoomList roomList = new RoomList(user.getUserId(), null, Role.MODERATOR, user, room);
*/
        roomRepo.save(room);
        log.info("IN createRoom room public room created");
    }

    /**
     * Метод удаления комнаты по id.
     * Доступно для ADMIN или Владельца комнаты.
     */
    public void deleteRoom(Long roomId) throws RoomNotFoundException, NoAccessException {
        if (roomRepo.findById(roomId).orElse(null) == null) {
            log.error("In deleteRoom room not found");
            throw new RoomNotFoundException("Комната не найдена");
        }
        log.info("In deleteRoom room successful deleted");
        roomRepo.deleteById(roomId);
    }

    /**
     * Метод переименования комнаты по id.
     * Доступно для ADMIN или Владельца комнаты.
     */
    public void renameRoom(Long roomId, String name) throws RoomNotFoundException, LogicException, NoAccessException {
        Room room = roomRepo.findById(roomId).orElse(null);
        if (room == null) {
            log.error("IN renameRoom room not found");
            throw new RoomNotFoundException("Комната не найдена");
        }
        if (room.getRoomName() == name) {
            log.error("IN renameRoom room is already so named");
            throw new LogicException("Комната уже так названа");
        }
        log.info("IN renameRoom room renamed");
        room.setRoomName(name);
    }
}

