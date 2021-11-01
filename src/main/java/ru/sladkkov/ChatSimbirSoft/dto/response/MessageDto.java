package ru.sladkkov.ChatSimbirSoft.dto.response;

import lombok.Data;
import ru.sladkkov.ChatSimbirSoft.domain.Message;

import java.sql.Timestamp;

@Data
public class MessageDto {
    private int message_id;

    private boolean read;

    private boolean deleted;

    private Timestamp messageTime;

    private String messageText;

    private int user_id;

    private int room_id;

    public Message toMessage(){
        Message message = new Message();
        message.setMessage_id(message_id);
        message.setMessageText(messageText);
        message.setRead(read);
        message.setDeleted(deleted);
        message.setMessageTime(messageTime);
        return message;
    }

    public MessageDto fromMessage(Message message){
        MessageDto messageDto = new MessageDto();
        messageDto.setMessage_id(message.getMessage_id());
        messageDto.setRead(message.isRead());
        messageDto.setDeleted(message.isDeleted());
        messageDto.setMessageTime(message.getMessageTime());
        messageDto.setMessageText(message.getMessageText());
        return messageDto;
    }

}
