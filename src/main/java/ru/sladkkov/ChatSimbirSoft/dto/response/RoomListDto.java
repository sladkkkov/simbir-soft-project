package ru.sladkkov.ChatSimbirSoft.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sladkkov.ChatSimbirSoft.domain.*;
import ru.sladkkov.ChatSimbirSoft.dto.request.UsersRequestDto;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomListDto {

    private RoomListId roomListId;

    private Timestamp banTime;

    private Role role;

}
