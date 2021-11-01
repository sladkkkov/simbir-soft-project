package ru.sladkkov.ChatSimbirSoft.dto.request;

import lombok.Data;
import ru.sladkkov.ChatSimbirSoft.domain.Message;
import ru.sladkkov.ChatSimbirSoft.dto.response.MessageDto;

@Data
public class MessageRequestDto {
    private int message_id;
    private int user_id;
    private int room_id;

    public Message toMessage() {
        Message message = new Message();
        message.setMessage_id(message_id);

        return message;
    }

    public MessageDto fromMessage(Message message) {
        MessageDto messageDto = new MessageDto();
        messageDto.setMessage_id(message.getMessage_id());
        return messageDto;
    }
}
