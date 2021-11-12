package ru.sladkkov.ChatSimbirSoft.dto.response;

import lombok.Data;

@Data
public class RoomDto {
    private long roomId;

    private long ownerId;

    private String roomName;
}
