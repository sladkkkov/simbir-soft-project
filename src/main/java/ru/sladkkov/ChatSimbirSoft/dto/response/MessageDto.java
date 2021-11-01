package ru.sladkkov.ChatSimbirSoft.dto.response;

import lombok.Data;

import java.security.Timestamp;

@Data
public class MessageDto {
    private int message_id;

    private boolean read;

    private boolean deleted;

    private Timestamp messageTime;

    private String messageText;

    private int user_id;

    private int room_id;

}
