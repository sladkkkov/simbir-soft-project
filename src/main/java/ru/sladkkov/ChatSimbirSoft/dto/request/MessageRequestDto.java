package ru.sladkkov.ChatSimbirSoft.dto.request;

import lombok.Data;
import ru.sladkkov.ChatSimbirSoft.domain.Room;
import ru.sladkkov.ChatSimbirSoft.domain.Users;

import java.sql.Timestamp;

@Data
public class MessageRequestDto {
    private long messageId;

    private boolean read;

    private boolean deleted;

    private Timestamp messageTime;

    private String messageText;

    private Room room;
}
