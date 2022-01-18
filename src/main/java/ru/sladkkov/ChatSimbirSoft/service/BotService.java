package ru.sladkkov.ChatSimbirSoft.service;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.sladkkov.ChatSimbirSoft.domain.Users;
import ru.sladkkov.ChatSimbirSoft.repository.MessageRepo;
import ru.sladkkov.ChatSimbirSoft.repository.RoomListRepo;
import ru.sladkkov.ChatSimbirSoft.repository.RoomRepo;
import ru.sladkkov.ChatSimbirSoft.repository.UserRepo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static ru.sladkkov.ChatSimbirSoft.service.BotService.State.yBot;


@Service
public class BotService {

    private final MessageRepo messageRepo;
    private final RoomRepo roomRepo;
    private final RoomListRepo roomListRepo;
    private final UserRepo userRepo;

    enum State {
        start,
        room, user, help, yBot, error, room_create, room_remove, room_rename, room_connect, room_disconnect, end, user_rename, user_ban, moderator, yBot_find, yBot_find_k, yBot_find_k_l, moderator_next, first

    }

    private final String regex = "\\/\\/(\\w{4})\\s+(\\w+)(\\s+\\{[\\w]+\\})?(\\s+-[rdklcnv]?)?" +
            "(\\s+\\{[\\w:\\/.=?-]+\\})?(\\s+-[ml])?(\\s+\\{[\\w+]\\})?(\\s+-[v]\\s+-[l])?";

    private final RoomService roomService;
    private final UsersService userService;
    private final RoomListService roomListService;

    public BotService(RoomRepo roomRepo, MessageRepo messageRepo, RoomRepo roomRepo1, RoomListRepo roomListRepo, UserRepo userRepo, RoomService roomService, UsersService userService, RoomListService roomListService) {
        this.messageRepo = messageRepo;
        this.roomRepo = roomRepo1;
        this.roomListRepo = roomListRepo;
        this.userRepo = userRepo;
        this.roomService = roomService;
        this.userService = userService;
        this.roomListService = roomListService;
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

   /* public String youtubeId(String command) throws IOException {
        if (command == null) {
            throw new IOException("Команда пустая, воспользуйтесь //help для просмотра списка команд");
        }
        List<String> strings = new ArrayList<>();

        Pattern pattern = Pattern.compile("^.*(youtu.be\\/|v\\/|embed\\/|watch\\?|youtube.com\\/user\\/[^#]*#([^\\/]*?\\/)*)\\??v?=?([^#\\&\\?]*).*");
        Matcher matcher = pattern.matcher(command);
        if (matcher.matches()) {
            strings.add(matcher.group(1));
            strings.add(matcher.group(2));
            strings.add(matcher.group(3));

        }
        return strings.get(2);
    }*/

    public List<String> getAllCommand() {

        List<String> list = new ArrayList<>();
        list.add("//room create {a} -c");
        list.add("//room create {a}");
        list.add("//room remove {a}");
        list.add("//room rename {a} {b}"); //TODO
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
        list.add("//yBot find -k {b}");
        list.add("//yBot find -k {a} -l {b} -v -l");
        list.add("//yBot help");
        return list;
    }

    public Object commandCreate(List<String> parsedCommand) throws Exception {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Users user = userRepo.findByUserLogin(userDetails.getUsername()).orElse(null);

        State state = State.start;
        String firstParam;
        String secondParam;
        if (parsedCommand.size() == 0) {
            state = State.error;
        }
        for (int i = 0; i < parsedCommand.size(); i++) {
            switch (state) {
                case start:
                    if (parsedCommand.get(i).equals("room")) {
                        state = State.room;
                    } else if (parsedCommand.get(i).equals("help")) {
                        state = State.help;
                    } else if (parsedCommand.get(i).equals("user")) {
                        state = State.user;
                    } else if (parsedCommand.get(i).equals("yBot")) {
                        state = yBot;
                    } else {
                        state = State.error;
                    }
                    break;

                case room:
                    if (parsedCommand.get(i).equals("create")) {
                        state = State.room_create;
                    } else if (parsedCommand.get(i).equals("remove")) {
                        state = State.room_remove;
                    } else if (parsedCommand.get(i).equals("rename")) {
                        state = State.room_rename;
                    } else if (parsedCommand.get(i).equals("connect")) {
                        state = State.room_connect;
                    } else if (parsedCommand.get(i).equals("disconnect")) {
                        state = State.room_disconnect;
                    } else {
                        state = State.error;
                    }
                    break;

                case help:
                    return getAllCommand();

                case user:
                    if (parsedCommand.get(i).equals("rename")) {
                        state = State.user_rename;
                    } else if (parsedCommand.get(i).equals("ban")) {
                        state = State.user_ban;
                    } else if (parsedCommand.get(i).equals("moderator")) {
                        state = State.moderator;
                    } else {
                        state = State.error;
                    }
                    break;
                case user_rename:
                    userService.updateUser(parsedCommand.get(2), parsedCommand.get(3));
                    return ResponseEntity.ok("Пользователь успешно переименован");
                case user_ban:
                    userService.blockUser(Long.parseLong(parsedCommand.get(2)));
                    return ResponseEntity.ok("Пользователь успешно забанен");
                case moderator:
                    state = State.moderator_next;
                    break;
                case moderator_next:
                    if (parsedCommand.size() == 4 && parsedCommand.get(3).equals("n")) {
                        userService.setModerator(Long.parseLong(parsedCommand.get(2)));
                    } else {
                        userService.deleteModerator(Long.parseLong(parsedCommand.get(2)));
                    }
                    return ResponseEntity.ok("Пользователь успешно обновлён");

                case yBot:
                    if (parsedCommand.get(i).equals("find")) {
                        state = State.yBot_find;
                    } else if (parsedCommand.get(i).equals("help")) {
                        state = State.help;
                    } else {
                        state = State.error;
                    }
                    break;
                case yBot_find:
                    if (parsedCommand.get(i).equals("k")) {
                        String string = null;
                        String pattern = "^.*(youtu.be\\/|v\\/|embed\\/|watch\\?|youtube.com\\/user\\/[^#]*#([^\\/]*?\\/)*)\\??v?=?([^#\\&\\?]*).*";
                        String pattern1 = "^.*(\\?v?)=?([^#\\&\\?]*).*";
                        Search youtube_binfo = new Search();

                        Pattern compiledPattern = Pattern.compile(pattern1);
                        Matcher matcher = compiledPattern.matcher("https://www.youtube.com/watch?v=vtPkZShrvXQ");
                        if(matcher.matches()){
                            string = matcher.group(2);
                        }
                        String str1 = string.replace("}", "");
                        return youtube_binfo.getyoutubeitemfull_details(str1);
                    } else {
                        state = State.error;
                    }
                    break;
                case yBot_find_k:
                    if (parsedCommand.get(i).equals("l")) {
                        state = State.yBot_find_k_l;
                    } else {
                        state = State.error;
                    }
                    break;
                case yBot_find_k_l:

                    if (parsedCommand.size() == 5) {
                        //TODO добавить апи ютуб
                    } else {
                        state = State.error;
                    }
                    break;
                case room_create:
                    if (parsedCommand.size() == 4 && parsedCommand.get(3).equals("c")) {
                        //создать добавление приватной комнаты;
                        roomService.createRoom(parsedCommand.get(2), "c");
                        return ResponseEntity.ok("Комната успешно создана");
                    } else if (parsedCommand.size() == 3) {
                        roomService.createRoom(parsedCommand.get(2), "public");
                        return ResponseEntity.ok("Комната успешно создана");
                    } else {
                        state = State.error;
                    }
                    break;
                case room_remove:
                    roomService.deleteRoom(Long.parseLong(parsedCommand.get(i)));
                    state = State.end;
                    return ResponseEntity.ok("Комната успешно удалена");

                case room_rename:
                    if (parsedCommand.size() == 4) {
                        roomService.renameRoom(Long.parseLong(parsedCommand.get(2)), parsedCommand.get(3));
                        return ResponseEntity.ok("Комната успешно переименована");
                    }
                    state = State.end;
                    break;
                case room_connect:
                    if (parsedCommand.size() == 5) {
                        roomListService.joinRoomList(Long.parseLong(parsedCommand.get(2)), parsedCommand.get(4));
                        return ResponseEntity.ok("Пользователь успещно добавлен в комнату");
                    }
                    state = State.end;
                    break;
                case room_disconnect:
                    if (parsedCommand.size() == 3) {
                        roomListService.deleteRoomList(Long.parseLong(parsedCommand.get(2)));
                    } else if (parsedCommand.size() == 5) {
                        roomListService.deleteRoomListByUserId(parsedCommand.get(4), Long.parseLong(parsedCommand.get(2)));
                    }

                    state = State.end;
                    break;
                //удалить пользователя из комнаты
                case error:
                    return ResponseEntity.ok("Ошибка, посмотрите список команд");
                default:
                    state = State.error;
            }
        }
        return ResponseEntity.ok("Ошибка, посмотрите список команд");
    }
}
