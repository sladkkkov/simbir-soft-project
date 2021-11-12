package ru.sladkkov.ChatSimbirSoft.service;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sladkkov.ChatSimbirSoft.domain.Room;
import ru.sladkkov.ChatSimbirSoft.dto.response.RoomDto;
import ru.sladkkov.ChatSimbirSoft.exception.LogicException;
import ru.sladkkov.ChatSimbirSoft.exception.RoomAlreadyCreatedException;
import ru.sladkkov.ChatSimbirSoft.exception.RoomNotFoundException;
import ru.sladkkov.ChatSimbirSoft.exception.UserBannedException;
import ru.sladkkov.ChatSimbirSoft.repository.RolesRepo;
import ru.sladkkov.ChatSimbirSoft.repository.RoomListRepo;
import ru.sladkkov.ChatSimbirSoft.repository.RoomRepo;
import ru.sladkkov.ChatSimbirSoft.repository.UserRepo;
import ru.sladkkov.ChatSimbirSoft.service.mapper.RoomMapper;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Log4j
@Transactional
public class RoomService {

    private final RoomRepo roomRepo;
    private final UserRepo userRepo;
    private final RolesRepo rolesRepo;
    private final RoomListRepo roomListRepo;

    @Autowired
    public RoomService(RoomRepo roomRepo, UserRepo userRepo, RolesRepo rolesRepo, RoomListRepo roomListRepo) {
        this.roomRepo = roomRepo;
        this.userRepo = userRepo;
        this.rolesRepo = rolesRepo;
        this.roomListRepo = roomListRepo;
    }

    public List<RoomDto> getAllRoom() throws RoomNotFoundException {
        if (roomRepo.findAll().size() == 0) {
            log.error("IN getAllRoom rooms not found");
            throw new RoomNotFoundException("Комнат не найдено");
        }
        log.info("IN getAllRoom rooms successfully founded");
        return RoomMapper.INSTANCE.toModelList(roomRepo.findAll());
    }

    public RoomDto getRoomById(Long id) throws RoomNotFoundException {
        Room room = roomRepo.findById(id).orElse(null);
        if (room == null) {
            log.error("IN getRoom room not found");
            throw new RoomNotFoundException("Комната не найдена");
        }
        log.info("IN getRoom room successfully founded");
        return RoomMapper.INSTANCE.toModel(room);
    }

    public void createRoom(RoomDto roomDto) throws RoomAlreadyCreatedException, UserBannedException {

        Room room = RoomMapper.INSTANCE.toEntity(roomDto);
        if (roomRepo.findById(room.getRoomId()).orElse(null) != null) {
            log.error("IN createRoom room already created");
            throw new RoomAlreadyCreatedException("Такая комната уже существует");
        }
        if (!userRepo.findById(room.getOwnerId()).orElse(null).isActive()) {
            log.error("IN createRoom create room failed, user don't Active");
            throw new UserBannedException("Пользователь забанен на сайте");
        }
        roomRepo.save(RoomMapper.INSTANCE.toEntity(roomDto));
        log.info("IN createRoom room created");

    }

    public void deleteRoom(Long id) throws RoomNotFoundException {
        if (roomRepo.findById(id).orElse(null) == null) {
            log.error("In deleteRoom room not found");
            throw new RoomNotFoundException("Комната не найдена");
        }
        log.info("In deleteRoom room successful deleted");
        roomRepo.deleteById(id);
    }

    public void renameRoom(Long roomId,Long userId, String name) throws RoomNotFoundException, LogicException {
        Room room = roomRepo.findById(roomId).orElse(null);
        if(room == null){
            log.error("IN renameRoom room not found");
            throw new RoomNotFoundException("Комната не найдена");
        }
        if(room.getRoomName() == name){
            log.error("IN renameRoom room is already so named ");
            throw new LogicException("Комната уже так названа");
        }
        log.info("IN renameRoom room renamed ");
        room.setRoomName(name);
    }
}

