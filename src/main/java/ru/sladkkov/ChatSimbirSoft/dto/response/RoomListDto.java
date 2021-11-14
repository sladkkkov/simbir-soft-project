package ru.sladkkov.ChatSimbirSoft.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sladkkov.ChatSimbirSoft.domain.Roles;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomListDto {

    private Long userId;

    private Timestamp banTime;

    private Roles roles;

}
