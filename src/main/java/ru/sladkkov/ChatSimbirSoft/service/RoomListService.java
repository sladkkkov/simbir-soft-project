package ru.sladkkov.ChatSimbirSoft.service;

import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import ru.sladkkov.ChatSimbirSoft.domain.RoomList;
import ru.sladkkov.ChatSimbirSoft.dto.response.RoomDto;
import ru.sladkkov.ChatSimbirSoft.dto.response.RoomListDto;
import ru.sladkkov.ChatSimbirSoft.exception.LogicException;
import ru.sladkkov.ChatSimbirSoft.exception.RoomAlreadyCreatedException;
import ru.sladkkov.ChatSimbirSoft.exception.RoomListNotFoundException;
import ru.sladkkov.ChatSimbirSoft.exception.UserBannedException;
import ru.sladkkov.ChatSimbirSoft.repository.RoomListRepo;
import ru.sladkkov.ChatSimbirSoft.repository.RoomRepo;
import ru.sladkkov.ChatSimbirSoft.repository.UserRepo;
import ru.sladkkov.ChatSimbirSoft.service.mapper.RoomListMapper;
import ru.sladkkov.ChatSimbirSoft.service.mapper.RoomMapper;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Log4j
@Transactional
public class RoomListService {

    private final RoomListRepo roomListRepo;
    private final UserRepo userRepo;
    private final RoomRepo roomRepo;


    public RoomListService(RoomListRepo roomListRepo, UserRepo userRepo, RoomRepo roomRepo) {
        this.roomListRepo = roomListRepo;
        this.userRepo = userRepo;
        this.roomRepo = roomRepo;
    }

    public List<RoomListDto> getAllRoomList() throws RoomListNotFoundException {
        if (roomListRepo.findAll().size() == 0) {
            log.error("IN getAllRoomList rooms not found");
            throw new RoomListNotFoundException("Списков не найдено");
        }
        log.info("IN getAllRoomList lists successfully founded");
        return RoomListMapper.INSTANCE.toModelList(roomListRepo.findAll());
    }

    public RoomListDto getRoomListById(Long id) throws RoomListNotFoundException {
        RoomList roomList = roomListRepo.findById(id).orElse(null);
        if (roomList == null) {
            log.error("IN getRoomListById roomLists not found");
            throw new RoomListNotFoundException("Списков не найдено");
        }
        log.info("IN getRoomListById roomLists successfully founded");
        return RoomListMapper.INSTANCE.toModel(roomList);
    }

    public void createRoomList(String name, String typeRoom, Long userId) throws RoomAlreadyCreatedException, UserBannedException, LogicException {
        if (!userRepo.findById(userId).orElse(null).isActive()) {
            log.error("IN createRoom create room failed, user don't Active");
            throw new UserBannedException("Пользователь забанен на сайте");
        }
        if(!typeRoom.equals("private") || !typeRoom.equals("public")){
            log.error("IN createRoomList create room failed type room");
            throw new LogicException("Тип комнаты private или public");
        }
        roomRepo.save(RoomMapper.INSTANCE.toEntity(new RoomDto(userId,name,typeRoom)));
        log.info("IN createRoom room created");
    }

    public void deleteRoomList(Long id) throws RoomListNotFoundException {
        if (roomListRepo.findById(id).orElse(null) == null) {
            log.error("In deleteRoomList roomLists not found");
            throw new RoomListNotFoundException("Список не найден");
        }
        log.info("In deleteRoomList roomLists successful deleted");
        roomListRepo.deleteById(id);
    }
}
