package ru.sladkkov.ChatSimbirSoft.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomDto {


    private long ownerId;

    private String roomName;

    private String roomType;

}
