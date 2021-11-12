package ru.sladkkov.ChatSimbirSoft.dto.request;

import lombok.Data;

@Data
public class MessageRequestDto {
    private long messageId;
    private long userId;
    private long roomId;

}
