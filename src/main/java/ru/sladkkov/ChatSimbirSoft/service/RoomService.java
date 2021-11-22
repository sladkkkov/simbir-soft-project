package ru.sladkkov.ChatSimbirSoft.service;

import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import ru.sladkkov.ChatSimbirSoft.domain.Room;
import ru.sladkkov.ChatSimbirSoft.dto.response.RoomDto;
import ru.sladkkov.ChatSimbirSoft.exception.*;
import ru.sladkkov.ChatSimbirSoft.mapper.RoomMapper;
import ru.sladkkov.ChatSimbirSoft.repository.RolesRepo;
import ru.sladkkov.ChatSimbirSoft.repository.RoomListRepo;
import ru.sladkkov.ChatSimbirSoft.repository.RoomRepo;
import ru.sladkkov.ChatSimbirSoft.repository.UserRepo;

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


    public RoomService(RoomRepo roomRepo, UserRepo userRepo, RolesRepo rolesRepo, RoomListRepo roomListRepo) {
        this.roomRepo = roomRepo;
        this.userRepo = userRepo;
        this.rolesRepo = rolesRepo;
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
    public void createRoom(String name, String typeRoom, Long userId) throws RoomAlreadyCreatedException, UserBannedException {
        if (!userRepo.findById(userId).orElse(null).getStatus().name().equals("ACTIVE")) {
            log.error("IN createRoom create room failed, user don't Active");
            throw new UserBannedException("Пользователь забанен на сайте");
        }
        if (typeRoom == "c"){
            RoomDto roomDto = new RoomDto(userId,name,"private");
            log.info("IN createRoom room private room created");

        }
        RoomDto roomDto = new RoomDto(userId,name,"public");
        roomRepo.save(RoomMapper.INSTANCE.toEntity(roomDto));
        log.info("IN createRoom room public room created");
    }
    /**
     * Метод удаления комнаты по id.
     * Доступно для ADMIN или Владельца комнаты.
     */
    public void deleteRoom(Long roomId, Long userId) throws RoomNotFoundException, NoAccessException {
        if (roomRepo.findById(roomId).orElse(null) == null) {
            log.error("In deleteRoom room not found");
            throw new RoomNotFoundException("Комната не найдена");
        }
        if(roomListRepo.getByUserIdAndRoomRoomId(userId,roomId).getRoles().getRole() != "ADMIN"
                ||  userId != roomRepo.findById(userId).orElse(null).getOwnerId()){
            log.error("IN renameRoom no access");
            throw new NoAccessException("Нет прав");
        }
        log.info("In deleteRoom room successful deleted");
        roomRepo.deleteById(roomId);
    }
    /**
     * Метод переименования комнаты по id.
     * Доступно для ADMIN или Владельца комнаты.
     */
    public void renameRoom(Long roomId,Long userId, String name) throws RoomNotFoundException, LogicException, NoAccessException {
        Room room = roomRepo.findById(roomId).orElse(null);
        if(room == null){
            log.error("IN renameRoom room not found");
            throw new RoomNotFoundException("Комната не найдена");
        }
        if(room.getRoomName() == name){
            log.error("IN renameRoom room is already so named");
            throw new LogicException("Комната уже так названа");
        }
        if(roomListRepo.getByUserIdAndRoomRoomId(userId,roomId).getRoles().getRole() != "ADMIN"
                ||  userId != roomRepo.findById(userId).orElse(null).getOwnerId()){
            log.error("IN renameRoom no access");
            throw new NoAccessException("Нет прав");
        }
        log.info("IN renameRoom room renamed");
        room.setRoomName(name);
    }
}

