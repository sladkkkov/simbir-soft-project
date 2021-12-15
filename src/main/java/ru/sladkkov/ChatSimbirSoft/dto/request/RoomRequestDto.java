package ru.sladkkov.ChatSimbirSoft.dto.request;

import lombok.Data;

@Data
public class RoomRequestDto {

    private long ownerId;

    private String roomName;

    private String roomType;
}
