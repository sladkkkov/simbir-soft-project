package ru.sladkkov.ChatSimbirSoft.service;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sladkkov.ChatSimbirSoft.domain.Room;
import ru.sladkkov.ChatSimbirSoft.exception.RoomAlreadyCreatedException;
import ru.sladkkov.ChatSimbirSoft.exception.RoomNotFoundException;
import ru.sladkkov.ChatSimbirSoft.repository.RoomRepo;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Log4j
@Transactional
public class RoomService {

    private final RoomRepo roomRepo;

    @Autowired
    public RoomService(RoomRepo roomRepo) {
        this.roomRepo = roomRepo;
    }

    public List<Room> getAllRoom() throws RoomNotFoundException {
        if (roomRepo.findAll().size() == 0) {
            log.error("IN getAllRoom rooms not found");
            throw new RoomNotFoundException("Комнат не найдено");
        }
        log.error("IN getAllRoom rooms successfully founded");
        return roomRepo.findAll();
    }

    public Room getRoomById(Long id) throws RoomNotFoundException {
        Room room = roomRepo.findById(id).orElse(null);
        if (room == null) {
            log.error("IN getRoom room not found");
            throw new RoomNotFoundException("Комната не найдена");
        }
        log.error("IN getRoom room successfully founded");
        return room;
    }

    public void createRoom(Room room) throws RoomAlreadyCreatedException {
        if (roomRepo.findById((room.getRoomId())).orElse(null) != null) {
            log.error("IN createRoom room already created");
            throw new RoomAlreadyCreatedException("Такая комната уже существует");
        }
        log.info("IN createRoom room created");
        roomRepo.save(room);
    }

    public void deleteRoom(Long id) throws RoomNotFoundException {
        if (roomRepo.findById(id).orElse(null) == null) {
            log.error("In deleteRoom room not found");
            throw new RoomNotFoundException("Комната не найдена");
        }
        log.error("In deleteRoom room successful deleted");
        roomRepo.deleteById(id);
    }
}

