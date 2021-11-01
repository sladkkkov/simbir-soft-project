package ru.sladkkov.ChatSimbirSoft.dto.response;

import lombok.Data;

@Data
public class Room {
    private int roomId;

    private int owner_id;

    private String room_name;

    private String roomType;

}
