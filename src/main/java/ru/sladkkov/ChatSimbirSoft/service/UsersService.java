package ru.sladkkov.ChatSimbirSoft.service;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sladkkov.ChatSimbirSoft.domain.Role;
import ru.sladkkov.ChatSimbirSoft.domain.Status;
import ru.sladkkov.ChatSimbirSoft.domain.Users;
import ru.sladkkov.ChatSimbirSoft.dto.request.UsersRequestDto;
import ru.sladkkov.ChatSimbirSoft.dto.response.UsersDto;
import ru.sladkkov.ChatSimbirSoft.exception.LogicException;
import ru.sladkkov.ChatSimbirSoft.exception.UserAlreadyCreatedException;
import ru.sladkkov.ChatSimbirSoft.exception.UserNotFoundException;
import ru.sladkkov.ChatSimbirSoft.mapper.UsersMapper;
import ru.sladkkov.ChatSimbirSoft.repository.RoomListRepo;
import ru.sladkkov.ChatSimbirSoft.repository.UserRepo;

import java.util.List;

@Service
@Log4j
@Transactional
public class UsersService {

    private final UserRepo userRepo;
    private final RoomListRepo roomListRepo;

    @Autowired
    public UsersService(UserRepo userRepo, RoomListRepo roomListRepo) {
        this.userRepo = userRepo;
        this.roomListRepo = roomListRepo;
    }

    /**
     * Метод получения всех пользователей.
     * Доступно для USER, MODERATOR, ADMIN, BLOCKED USER.
     */
    public List<UsersRequestDto> getAll() throws UserNotFoundException {
        List<Users> usersList = userRepo.findAll();
        if (usersList.size() == 0) {
            log.error("IN getAll users not found");
            throw new UserNotFoundException("Пользователей не найдено");
        }
        log.info("IN getAll " + usersList.size() + " users found");
        return UsersMapper.userInstance.toModelList(usersList);
    }

    /**
     * Метод получения пользователя по id.
     */
    public UsersRequestDto getById(Long id) throws UserNotFoundException {
        Users user = userRepo.findById(id).orElse(null);
        if (user == null) {
            log.error("IN getById user by ID: " + id + " not found");
            throw new UserNotFoundException("Пользователь с таким id не найден");
        }
        log.info("IN getById user by ID " + id + " found");
        return UsersMapper.userInstance.toModel(user);
    }

    /**
     * Метод получения пользователя по name.
     */
    public UsersRequestDto findByUserLogin(String login) throws UserNotFoundException {
        Users user = userRepo.findByUserLogin(login).orElseThrow(()
                -> new UserNotFoundException("Пользователь с таким именем не найден"));
        log.info("IN getById user by name " + login + " found");
        return UsersMapper.userInstance.toModel(user);
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
     * Метод обновления пользователя по login.
     */
    public void updateUser(String login, String newLogin) throws UserNotFoundException {
        Users user = userRepo.findByUserLogin(login).orElse(null);
        if (user == null) {
            log.error("IN updateUser user by login: " + login + " not found");
            throw new UserNotFoundException("Пользователь с таким login не найден");
        }
        user.setUserLogin(newLogin);
        log.info("IN deleteUser user with " + login + " found and successful deleted");

    }

    /**
     * Метод регистрации пользователя.
     */
    public void createUsers(UsersDto usersDto) throws UserAlreadyCreatedException {
        if (userRepo.findByUserLogin(usersDto.getUserLogin()).orElse(null) != null) {
            log.error("IN createUser user already created");
            throw new UserAlreadyCreatedException("Такой пользователь уже существует");
        }
        Users user1 = UsersMapper.userInstance.toEntity(usersDto);
        user1.setRole(Role.USER);
        user1.setStatus(Status.ACTIVE);
        user1.setUserPassword(new BCryptPasswordEncoder(12).encode(usersDto.getUserPassword()));
        log.info("IN createUser user created");
        userRepo.save(user1);
    }

    /**
     * Метод бана пользователя по id.
     * Доступно для MODERATOR, ADMIN.
     */
    public void blockUser(Long userId) throws UserNotFoundException, LogicException {
        Users user = userRepo.findById(userId).orElse(null);
        if (user == null) {
            log.error("IN blockUser user by ID: " + userId + " not found");
            throw new UserNotFoundException("Пользователь с таким id не найден");
        }
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userDetails.getUsername().equals(user.getUserLogin())) {
            log.error("IN blockUser Administrator and user match");
            throw new LogicException("Administrator не может забанить сам себя");
        }
        if (user.getStatus().equals(Status.BANNED)) {
            log.error("IN blockUser user by ID: " + userId + " already blocked on site");
            throw new LogicException("Пользователь с таким id уже заблокирован на сайте");
        }
        log.info("IN blockUser user was blocked");
        userRepo.getById(userId).setStatus(Status.BANNED);


    }

    /**
     * Метод разблокировки пользователя по id.
     * Доступно для MODERATOR, ADMIN.
     */
    public void unblockUser(Long userId) throws UserNotFoundException, LogicException {
        Users user = userRepo.findById(userId).orElse(null);
        if (user == null) {
            log.error("IN unblockUser user by ID: " + userId + " not found");
            throw new UserNotFoundException("Пользователь с таким id не найден");
        }
        if (user.getStatus().equals(Status.ACTIVE)) {
            log.error("IN unblockUser user by ID: " + userId + "  unblock on site");
            throw new UserNotFoundException("Пользователь с таким id не заблокирован на сайте");
        }
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userRepo.findByUserLogin(userDetails.getUsername()).equals(user)) {
            log.error("IN unblockUser moderatorOrAdministrator and user match");
            throw new LogicException("moderatorOrAdministrator не может разбанить сам себя");
        }
        log.info("IN unblockUser user was unlocked");
        userRepo.getById(userId).setStatus(Status.ACTIVE);
    }

    /**
     * Метод назначеня модератора.
     * Доступно для ADMIN.
     */
    public void setModerator(Long userId) throws UserNotFoundException {
        Users user = userRepo.findById(userId).orElse(null);
        if (user == null) {

            log.error("IN setModerator user by ID: " + userId + " not found");
            throw new UserNotFoundException("Пользователь с таким id не найден");
        }
        if (user.getStatus().equals(Status.BANNED)) {
            log.error("IN setModerator user by ID: " + userId + "  blocked on site");
            throw new UserNotFoundException("Пользователь с таким id заблокирован на сайте");
        }
        user.setRole(Role.ADMIN);
        log.info("IN setModerator setModerator");
    }

    /**
     * Метод снятия прав модератора.
     * Доступно для ADMIN.
     */
    public void deleteModerator(Long userId) throws
            UserNotFoundException {
        Users user = userRepo.findById(userId).orElse(null);
        if (user == null) {
            log.error("IN deleteModerator user by ID: " + userId + " not found");
            throw new UserNotFoundException("Пользователь с таким id не найден");
        }
        if (user.getStatus().equals(Status.BANNED)) {
            log.error("IN deleteModerator user by ID: " + userId + "  blocked on site");
            throw new UserNotFoundException("Пользователь с таким id заблокирован на сайте");
        }
        user.setRole(Role.USER);
        log.info("IN deleteModerator deleteModerator");
    }
}

