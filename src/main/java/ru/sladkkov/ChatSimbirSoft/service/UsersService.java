package ru.sladkkov.ChatSimbirSoft.service;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sladkkov.ChatSimbirSoft.domain.Role;
import ru.sladkkov.ChatSimbirSoft.domain.Status;
import ru.sladkkov.ChatSimbirSoft.domain.Users;
import ru.sladkkov.ChatSimbirSoft.dto.response.UsersDto;
import ru.sladkkov.ChatSimbirSoft.exception.LogicException;
import ru.sladkkov.ChatSimbirSoft.exception.NoAccessException;
import ru.sladkkov.ChatSimbirSoft.exception.UserAlreadyCreatedException;
import ru.sladkkov.ChatSimbirSoft.exception.UserNotFoundException;
import ru.sladkkov.ChatSimbirSoft.mapper.UsersMapper;
import ru.sladkkov.ChatSimbirSoft.repository.RoomListRepo;
import ru.sladkkov.ChatSimbirSoft.repository.UserRepo;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Log4j
@Transactional
public class UsersService {

    private final UserRepo userRepo;
    private final RoomListRepo roomListRepo;

    @Autowired
    public UsersService(UserRepo userRepo, RoomListRepo roomListRepo  ) {
        this.userRepo = userRepo;
        this.roomListRepo = roomListRepo;
    }



    /**
     * Метод получения всех пользователей.
     * Доступно для USER, MODERATOR, ADMIN, BLOCKED USER.
     */
    public List<UsersDto> getAll() throws UserNotFoundException {
        List<Users> usersList = userRepo.findAll();
        if (usersList.size() == 0) {
            log.error("IN getAll users not found");
            throw new UserNotFoundException("Пользователей не найдено");
        }
        log.info("IN getAll " + usersList.size() + " users found");
        return UsersMapper.INSTANCE.toModelList(usersList);
    }

    /**
     * Метод получения пользователя по id.
     */
    public UsersDto getById(Long id) throws UserNotFoundException {
        Users user = userRepo.findById(id).orElse(null);
        if (user == null) {
            log.error("IN getById user by ID: " + id + " not found");
            throw new UserNotFoundException("Пользователь с таким id не найден");
        }
        log.info("IN getById user by ID " + id + " found");
        return UsersMapper.INSTANCE.toModel(user);
    }

    /**
     * Метод получения пользователя по name.
     */
    public UsersDto findByUserLogin(String login) throws UserNotFoundException {
        Users user = userRepo.findByUserLogin(login).orElseThrow(()
                -> new UserNotFoundException("Пользователь с таким name не найден"));
        log.info("IN getById user by name " + login + " found");
        return UsersMapper.INSTANCE.toModel(user);
    }

    /**
     * Метод удаления пользователя по id.
     */
    public void deleteUser(Long id) throws UserNotFoundException {
        Users user = userRepo.findById(id).orElse(null);
        if (user == null) {
            log.error("IN deleteUser user by ID: " + id + " not found");
            throw new UserNotFoundException("Пользователь с таким id не найден");
        }
        log.info("IN deleteUser user with " + id + " found and successful deleted");
        userRepo.deleteById(id);
    }

    /**
     * Метод регистрации пользователя.
     */
    public void createUsers(UsersDto usersDto) throws UserAlreadyCreatedException {
        Users user = userRepo.findById(UsersMapper.INSTANCE.toEntity(usersDto).getUserId()).orElse(null);
       if (user != null) {
            log.error("IN createUser user already created");
            throw new UserAlreadyCreatedException("Такой пользователь уже существует");
        }
        user.setRole(Role.USER);
        user.setStatus(Status.ACTIVE);
        log.info("IN createUser user created");
        userRepo.save(user);
    }

    /**
     * Метод бана пользователя по id.
     * Доступно для MODERATOR, ADMIN.
     */
    public void blockUser(Long userId, Long moderatorOrAdministratorId, Long roomId) throws UserNotFoundException, LogicException, NoAccessException {
        Users user = userRepo.findById(userId).orElse(null);
        if (user == null) {
            log.error("IN blockUser user by ID: " + userId + " not found");
            throw new UserNotFoundException("Пользователь с таким id не найден");
        }
        if (user.getStatus().name().equals(Status.BANNED)) {
            log.error("IN blockUser user by ID: " + userId + " already blocked on site");
            throw new UserNotFoundException("Пользователь с таким id уже заблокирован на сайте");
        }
        if (moderatorOrAdministratorId == user.getUserId()) {
            log.error("IN blockUser moderatorOrAdministrator and user match");
            throw new LogicException("moderatorOrAdministrator не может забанить сам себя");
        }
        if (roomListRepo.getByUserIdAndRoomRoomId(moderatorOrAdministratorId, roomId).getRoles().getRole() == "USER") {
            log.error("IN blockUser no access");
            throw new NoAccessException("Нет доступа");
        }
        log.info("IN blockUser user was blocked");
        userRepo.getById(userId).setStatus(Status.BANNED);
    }

    /**
     * Метод разблокировки пользователя по id.
     * Доступно для MODERATOR, ADMIN.
     */
    public void unblockUser(Long userId, Long moderatorOrAdministratorId, Long roomId) throws UserNotFoundException, LogicException, NoAccessException {
        Users user = userRepo.findById(userId).orElse(null);
        Users moderatorOrAdministrator = userRepo.findById(moderatorOrAdministratorId).orElse(null);
        if (user == null) {
            log.error("IN unblockUser user by ID: " + userId + " not found");
            throw new UserNotFoundException("Пользователь с таким id не найден");
        }
        if (user.getStatus().equals(Status.BANNED)) {
            log.error("IN unblockUser user by ID: " + userId + "  unblock on site");
            throw new UserNotFoundException("Пользователь с таким id не заблокирован на сайте");
        }
        if (moderatorOrAdministratorId == user.getUserId()) {
            log.error("IN unblockUser moderatorOrAdministrator and user match");
            throw new LogicException("moderatorOrAdministrator не может разбанить сам себя");
        }
        if (roomListRepo.getByUserIdAndRoomRoomId(moderatorOrAdministratorId, roomId).getRoles().getRole() == "USER") {
            log.error("IN blockUser no access");
            throw new NoAccessException("Нет доступа");
        }
        log.info("IN unblockUser user was unlocked");
        userRepo.getById(userId).setStatus(Status.ACTIVE);
    }

    /**
     * Метод назначеня модератора.
     * Доступно для ADMIN.
     */
    public void setModerator(Long userId, Long administratorId, Long roomId) throws UserNotFoundException, LogicException, NoAccessException {
        Users user = userRepo.findById(userId).orElse(null);
        if (user == null) {
            log.error("IN setModerator user by ID: " + userId + " not found");
            throw new UserNotFoundException("Пользователь с таким id не найден");
        }
        if (user.getStatus().equals(Status.BANNED)) {
            log.error("IN setModerator user by ID: " + userId + "  blocked on site");
            throw new UserNotFoundException("Пользователь с таким id заблокирован на сайте");
        }
        if (!roomListRepo.getByUserIdAndRoomRoomId(userId, roomId).getRoles().getRole().equals("USER")) {
            log.error("IN setModerator userId is user");
            throw new LogicException("Такой пользователь не может являться модератором");
        }
        if (!roomListRepo.getByUserIdAndRoomRoomId(administratorId, roomId).getRoles().getRole().equals("ADMIN")) {
            log.error("IN setModerator no access");
            throw new NoAccessException("Нет доступа");
        }
        log.info("IN setModerator setModerator");
        roomListRepo.getByUserIdAndRoomRoomId(userId, roomId).getRoles().setRole("MODERATOR");
    }

    /**
     * Метод снятия прав модератора.
     * Доступно для ADMIN.
     */
    public void deleteModerator(Long userId, Long administratorId, Long roomId) throws
            UserNotFoundException, LogicException, NoAccessException {
        Users user = userRepo.findById(userId).orElse(null);
        if (user == null) {
            log.error("IN deleteModerator user by ID: " + userId + " not found");
            throw new UserNotFoundException("Пользователь с таким id не найден");
        }
        if (user.getStatus().equals(Status.BANNED)) {
            log.error("IN deleteModerator user by ID: " + userId + "  blocked on site");
            throw new UserNotFoundException("Пользователь с таким id заблокирован на сайте");
        }
        if (roomListRepo.getByUserIdAndRoomRoomId(userId, roomId).getRoles().getRole().equals("USER")) {
            log.error("IN deleteModerator userId is user");
            throw new LogicException("Такой пользователь не может являться модератором");
        }
        if (!roomListRepo.getByUserIdAndRoomRoomId(administratorId, roomId).getRoles().getRole().equals("ADMIN")) {
            log.error("IN deleteModerator no access");
            throw new NoAccessException("Нет доступа");
        }
        log.info("IN deleteModerator deleteModerator");
        roomListRepo.getByUserIdAndRoomRoomId(userId, roomId).getRoles().setRole("USER");
    }
}

