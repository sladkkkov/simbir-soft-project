package ru.sladkkov.ChatSimbirSoft.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.sladkkov.ChatSimbirSoft.exception.LogicException;
import ru.sladkkov.ChatSimbirSoft.exception.NoAccessException;
import ru.sladkkov.ChatSimbirSoft.exception.RoomNotFoundException;
import ru.sladkkov.ChatSimbirSoft.repository.RoomRepo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static ru.sladkkov.ChatSimbirSoft.service.BotService.State.yBot;


@Service
public class BotService {

    enum State {
        start,
        room, user, help, yBot, error, room_create, room_remove, room_rename, room_connect, room_disconnect, end, user_rename, user_ban, moderator, yBot_find, first

    }

    private final String regex = "\\/\\/(\\w{4})\\s+(\\w+)(\\s+\\{[\\w]+\\})?(\\s+-[rdklcnv]?)" +
            "(\\s+\\{\\w+\\})?(\\s+-[ml])?(\\s+\\{[\\w+]\\})?(\\s+-[v]\\s+-[l])?";

    private final RoomService roomService;
    private final UsersService userService;

    public BotService(RoomRepo roomRepo, RoomService roomService, UsersService userService) {
        this.roomService = roomService;
        this.userService = userService;
    }

    public List<String> parserCommand(String command) throws IOException {
        if (command == null) {
            throw new IOException("Команда пустая, воспользуйтесь //help для просмотра списка команд");
        }

        List<String> strings = new ArrayList<>();
        List<String> strings1 = new ArrayList<>();

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(command);
        if (matcher.matches()) {
            strings.add(matcher.group(1));
            strings.add(matcher.group(2));
            strings.add(matcher.group(3));
            strings.add(matcher.group(4));
            strings.add(matcher.group(5));
            strings.add(matcher.group(6));
            strings.add(matcher.group(7));
            strings.add(matcher.group(8));
        }

        for (String string1 : strings) {
            if (string1 != null) {
                strings1.add(string1.replaceAll("\\W+", ""));
            }
        }
        return strings1;
    }

    public List<String> getAllCommand() {

        List<String> list = new ArrayList<>();
        list.add("//room create {a} -c");
        list.add("//room create {a}");
        list.add("//room remove {a}");
        list.add("//room rename {a}");
        list.add("//room connect {a}");
        list.add("//room connect {a} -l");
        list.add("//room disconnect");
        list.add("//room disconnect {testRoom}");
        list.add("//room disconnect {testRoom} -l {user} -m {5}");
        list.add("//user rename {login} -r {newname}");
        list.add("//room disconnect {testRoom} -l {user_1} -m {5}");
        list.add("//user ban -l {user} -m {5}");
        list.add("//user moderator {user} -n");
        list.add("//user moderator {user} -d");
        list.add("//yBot find -k {a} -l {b} -v -l");
        list.add("//yBot help");
        return list;
    }

    public Object commandCreate(List<String> parsedCommand) throws NoAccessException, RoomNotFoundException, LogicException {


        State state = State.start;

        for (int i = 0; i < parsedCommand.size(); i++) {
            switch (state) {
                case start:
                    if (parsedCommand.get(i) == "room") {
                        state = State.room;
                    } else if (parsedCommand.get(i) == "help") {
                        state = State.help;
                    } else if (parsedCommand.get(i) == "user") {
                        state = State.user;
                    } else if (parsedCommand.get(i) == "yBot") {
                        state = yBot;
                    } else {
                        state = State.error;
                    }
                    break;

                case room:
                    if (parsedCommand.get(i) == "create") {
                        state = State.room_create;
                    } else if (parsedCommand.get(i) == "remove") {
                        state = State.room_remove;
                    } else if (parsedCommand.get(i) == "rename") {
                        state = State.room_rename;
                    } else if (parsedCommand.get(i) == "connect") {
                        state = State.room_connect;
                    } else if (parsedCommand.get(i) == "disconnect") {
                        state = State.room_disconnect;
                    } else {
                        state = State.error;
                    }
                    break;

                case help:
                    return getAllCommand();

                case user:
                    if (parsedCommand.get(i) == "rename") {
                        state = State.user_rename;
                    } else if (parsedCommand.get(i) == "ban") {
                        state = State.user_ban;
                    } else if (parsedCommand.get(i) == "moderator") {
                        state = State.moderator;
                    } else {
                        state = State.error;
                    }
                    break;
                case yBot:
                    if (parsedCommand.get(i) == "find") {
                        state = State.yBot_find;
                    } else if (parsedCommand.get(i) == "help") {
                        state = State.help;
                    } else {
                        state = State.error;
                    }
                    break;
                case room_create:
                    if (parsedCommand.get(i + 1) == "c") {
                        //создать добавление приватной комнаты;
                    } else if (parsedCommand.get(i + 1) == "p") {
                        //создать добавление публичной комнаты;
                    } else {
                        state = State.error;
                    }
                    break;
                case room_remove:
                    state = State.end;
                    roomService.deleteRoom(Long.parseLong(parsedCommand.get(i)));
                    return ResponseEntity.ok("Комната успешно удалена");
                case room_rename:
                    state = State.end;
                    roomService.renameRoom(Long.parseLong(parsedCommand.get(i)), parsedCommand.get(i + 1));
                    return ResponseEntity.ok("Комната успешно переименована");

                case room_disconnect:
                    state = State.end;
                    //удалить пользователя из комнаты
                default:
                    state = State.error;
                    break;

            }

        }
        return ResponseEntity.ok("");
    }
}
