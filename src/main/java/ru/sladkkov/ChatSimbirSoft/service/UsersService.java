package ru.sladkkov.ChatSimbirSoft.service;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sladkkov.ChatSimbirSoft.domain.Users;
import ru.sladkkov.ChatSimbirSoft.dto.response.UsersDto;
import ru.sladkkov.ChatSimbirSoft.exception.UserAlreadyCreatedException;
import ru.sladkkov.ChatSimbirSoft.exception.UserNotFoundException;
import ru.sladkkov.ChatSimbirSoft.repository.UserRepo;
import ru.sladkkov.ChatSimbirSoft.service.mapper.UsersMapper;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Log4j
@Transactional
public class UsersService {

    @Autowired
    private  UserRepo userRepo;

    //Посмотреть список всех пользователей
    public List<UsersDto> getAll() throws UserNotFoundException {
        List<Users> usersList = userRepo.findAll();
        if(usersList.size() == 0){
            log.error("IN getAll users not found");
            throw new UserNotFoundException("Пользователей не найдено");
        }
        log.info("IN getAll " + usersList.size() + " users found");
        return UsersMapper.INSTANCE.toModelList(usersList);
    }

    //Посмотреть конкретного пользователя
    public UsersDto getById(Long id) throws UserNotFoundException {
        Users user = userRepo.findById(id).orElse(null);
        if (user == null) {
            log.error("IN getById user by ID: " + id + " not found");
            throw new UserNotFoundException("Пользователь с таким id не найден");
        }
        log.info("IN getById user by ID " + id + " found");
        return UsersMapper.INSTANCE.toModel(user);
    }

    //Удалить пользователя
    public void deleteUser(Long id) throws UserNotFoundException {
        Users user = userRepo.findById(id).orElse(null);
        if (user == null) {
            log.error("IN deleteUser user by ID: " + id + " not found");
            throw new UserNotFoundException("Пользователь с таким id не найден");
        }
        log.info("IN deleteUser user with " + id + " found and successful deleted");
        userRepo.deleteById(id);
    }

    //Создать пользователя
    public void createUsers(UsersDto usersDto) throws UserAlreadyCreatedException {
        if (userRepo.findById(UsersMapper.INSTANCE.toEntity(usersDto).getUserId()).orElse(null) != null) {
            log.error("IN createUser user already created");
            throw new UserAlreadyCreatedException("Такой пользователь уже существует");
        }
        log.info("IN createUser user created");
        userRepo.save(UsersMapper.INSTANCE.toEntity(usersDto));
    }

    /*//Блокировка пользователя
    public void blockUser(Long userId, Long roomId,Long moderatorId) throws UserNotFoundException, LogicException {
        Users user = userRepo.findById(userId).orElse(null);
        Users moderator = userRepo.findById(moderatorId).orElse(null);
        if (user == null) {
            log.error("IN blockUser user by ID: " + userId + " not found");
            throw new UserNotFoundException("Пользователь с таким id не найден");
        }
        if(roomListRepo.findByUserIdAndRoomId(userId, roomId) == null){
            log.error("IN blockUser user by ID: " + userId + " and roomId: " + roomId + " not found");
            throw new UserNotFoundException("Пользователь с таким id в заданной комнате не найден");
        }
        if(!user.isActive()){
            log.error("IN blockUser user by ID: " + userId + " already blocked on site");
            throw new UserNotFoundException("Пользователь с таким id заблокирован на сайте");
        }
        if(moderator == null ){
            log.error("IN blockUser moderator  ID : " + moderatorId + " not found");
            throw new UserNotFoundException("Модератор с таким id не найден");
        }
        if(moderator.equals(user)){
            log.error("IN blockUser moderator and user match");
            throw new LogicException("Модератор не может забанить сам себя");
        }
    }*/
}

