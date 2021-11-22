package ru.sladkkov.ChatSimbirSoft.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sladkkov.ChatSimbirSoft.domain.Roles;
import ru.sladkkov.ChatSimbirSoft.domain.Room;
import ru.sladkkov.ChatSimbirSoft.domain.Users;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomListDto {

    private Long userId;

    private Timestamp banTime;

    private Users users;

    private Room room;

    private Roles roles;

}
