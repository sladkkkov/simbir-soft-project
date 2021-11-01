package ru.sladkkov.ChatSimbirSoft.dto.response;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class RoomListDto {

    private int userId;

    private Timestamp ban_time;

    private int roomId;

    private String role;

}
