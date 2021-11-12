package ru.sladkkov.ChatSimbirSoft.dto.response;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class MessageDto {
    private long messageId;

    private boolean read;

    private boolean deleted;

    private Timestamp messageTime;

    private String messageText;

    private long userId;



}
