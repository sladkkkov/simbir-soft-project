package ru.sladkkov.ChatSimbirSoft.service;

import lombok.extern.log4j.Log4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.sladkkov.ChatSimbirSoft.domain.*;
import ru.sladkkov.ChatSimbirSoft.dto.response.RoomListDto;
import ru.sladkkov.ChatSimbirSoft.exception.LogicException;
import ru.sladkkov.ChatSimbirSoft.exception.RoomListNotFoundException;
import ru.sladkkov.ChatSimbirSoft.exception.UserBannedException;
import ru.sladkkov.ChatSimbirSoft.repository.RoomListRepo;
import ru.sladkkov.ChatSimbirSoft.repository.RoomRepo;
import ru.sladkkov.ChatSimbirSoft.repository.UserRepo;

import javax.transaction.Transactional;
import java.util.ArrayList;
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

    public RoomListDto toModel(RoomList roomList) {
        if (roomList == null) {
            return null;
        }

        RoomListDto roomListDto = new RoomListDto();
        roomListDto.setRoomListId(roomList.getId());
        roomListDto.setBanTime(roomList.getBanTime());
        roomListDto.setRole(roomList.getRole());

        return roomListDto;
    }
    public List<RoomListDto> toModelList(List<RoomList> roomListList) {
        if (roomListList == null) {
            return null;
        }

        List<RoomListDto> list = new ArrayList<RoomListDto>(roomListList.size());
        for (RoomList roomList : roomListList) {
            list.add(toModel(roomList));
        }

        return list;
    }
    public RoomList toEntity(RoomListDto roomListDto) {
        if ( roomListDto == null ) {
            return null;
        }

        RoomList roomList = new RoomList();
        roomList.setId(roomListDto.getRoomListId());
        roomList.setBanTime( roomListDto.getBanTime() );
        roomList.setRole( roomListDto.getRole() );

        return roomList;
    }

    public List<RoomListDto> getAllRoomList() throws RoomListNotFoundException {
        if (roomListRepo.findAll().size() == 0) {
            log.error("IN getAllRoomList rooms not found");
            throw new RoomListNotFoundException("Списков не найдено");
        }
        log.info("IN getAllRoomList lists successfully founded");
        return toModelList(roomListRepo.findAll());
    }

    public RoomListDto getRoomListByUserId(Long roomId) throws RoomListNotFoundException {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Users user = userRepo.findByUserLogin(userDetails.getUsername()).orElse(null);
        RoomList roomList = roomListRepo.findById(new RoomListId(roomId, user.getUserId())).orElse(null);
        if (roomList == null) {
            log.error("IN getRoomListById roomLists not found");
            throw new RoomListNotFoundException("Списков не найдено");
        }
        log.info("IN getRoomListById roomLists successfully founded");
        return toModel(roomList);
    }


    public ResponseEntity createRoomList(Long roomId) throws UserBannedException, LogicException {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Users user = userRepo.findByUserLogin(userDetails.getUsername()).orElse(null);

        if(roomListRepo.findById(new RoomListId(roomId, user.getUserId())).orElse(null) != null){
            log.error("IN createRoom create room failed");
            throw new UserBannedException("Пользователь уже в комнате");
        }
        if(roomRepo.findById(roomId).orElse(null) == null){
            log.error("IN createRoom create room failed");
            throw new UserBannedException("Комнаты с таким id не существет");
        }

        roomListRepo.save(new RoomList(new RoomListId(roomId,user.getUserId()), null, Role.USER));
        log.info("IN createRoom room created");
        return ResponseEntity.ok("Пользователь успешно добавлен в комнату");

    }

    public ResponseEntity joinRoomList(Long roomId, String userLogin) throws UserBannedException, LogicException {
        Users user = userRepo.findByUserLogin(userLogin).orElse(null);
        if (user.getStatus().equals(Status.BANNED)) {
            log.error("IN createRoom create room failed, user don't Active");
            throw new UserBannedException("Пользователь забанен на сайте");
        }
        if(roomListRepo.findById(new RoomListId(roomId, user.getUserId())).orElse(null) != null){
            log.error("IN createRoom create room failed");
            throw new UserBannedException("Пользователь уже в комнате");
        }
        if(roomRepo.findById(roomId).orElse(null) == null){
            log.error("IN createRoom create room failed");
            throw new UserBannedException("Комнаты с таким id не существет");
        }
        if(user == null){
            log.error("IN createRoom create room failed");
            throw new UserBannedException("Комнаты с таким id не существет");
        }

        roomListRepo.save(new RoomList(new RoomListId(roomId,user.getUserId()), null, Role.USER));
        log.info("IN createRoom room created");
        return ResponseEntity.ok("Пользователь успешно добавлен в комнату");

    }

    public void deleteRoomList(Long roomId) throws RoomListNotFoundException {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Users user = userRepo.findByUserLogin(userDetails.getUsername()).orElse(null);
        if (roomListRepo.findById(new RoomListId(roomId, user.getUserId())).orElse(null) == null) {
            log.error("In deleteRoomList roomLists not found");
            throw new RoomListNotFoundException("Юзер не состоит в комнате");
        }

        log.info("In deleteRoomList roomLists successful deleted");
        roomListRepo.deleteById(new RoomListId(roomId, user.getUserId()));
    }
    public void deleteRoomListByUserId(String userId, Long roomId) throws RoomListNotFoundException {
        Users user = userRepo.findByUserLogin(userId).orElse(null);
        if (roomListRepo.findById(new RoomListId(roomId, user.getUserId())).orElse(null) == null) {
            log.error("In deleteRoomList roomLists not found");
            throw new RoomListNotFoundException("Юзер не состоит в комнате");
        }

        log.info("In deleteRoomList roomLists successful deleted");
        roomListRepo.deleteById(new RoomListId(roomId,user.getUserId()));
    }
}
