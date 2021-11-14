package ru.sladkkov.ChatSimbirSoft.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sladkkov.ChatSimbirSoft.domain.Room;
import ru.sladkkov.ChatSimbirSoft.domain.Users;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDto {
    private long messageId;

    private boolean read;

    private boolean deleted;

    private Timestamp messageTime;

    private String messageText;

    private Users users;

    private Room room;

}
