package ru.sladkkov.ChatSimbirSoft.dto.response;

import lombok.Data;
import ru.sladkkov.ChatSimbirSoft.domain.Message;

import java.sql.Timestamp;

@Data
public class MessageDto {
    private long messageId;

    private boolean read;

    private boolean deleted;

    private Timestamp messageTime;

    private String messageText;

    private long userId;

    public Message toMessage(){
        Message message = new Message();
        message.setMessageId(messageId);
        message.setMessageText(messageText);
        message.setRead(read);
        message.setDeleted(deleted);
        message.setMessageTime(messageTime);
        return message;
    }

    public static MessageDto fromMessage(Message message){
        MessageDto messageDto = new MessageDto();
        messageDto.setMessageId(message.getMessageId());
        messageDto.setRead(message.isRead());
        messageDto.setDeleted(message.isDeleted());
        messageDto.setMessageTime(message.getMessageTime());
        messageDto.setMessageText(message.getMessageText());
        return messageDto;
    }

}
