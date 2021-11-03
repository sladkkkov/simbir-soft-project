package ru.sladkkov.ChatSimbirSoft.dto.request;

import lombok.Data;
import ru.sladkkov.ChatSimbirSoft.domain.Message;

@Data
public class MessageRequestDto {
    private long messageId;
    private long userId;
    private long roomId;

    public Message toMessage() {
        Message message = new Message();
        message.setMessageId(messageId);

        return message;
    }

    public MessageRequestDto fromMessage(Message message) {
        MessageRequestDto messageRequestDto = new MessageRequestDto();
        messageRequestDto.setMessageId(message.getMessageId());
        return messageRequestDto;
    }
}
