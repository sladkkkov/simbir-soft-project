package ru.sladkkov.ChatSimbirSoft.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.sladkkov.ChatSimbirSoft.domain.Message;
import ru.sladkkov.ChatSimbirSoft.dto.response.MessageDto;

import java.util.List;

@Mapper(uses = {RoomMapper.class, UsersMapper.class})
public interface MessageMapper {

    MessageMapper INSTANCE = Mappers.getMapper(MessageMapper.class);

    @Mapping(target = "room.messageList", ignore = true)
    @Mapping(target = "users.messageList", ignore = true)
    @Mapping(target = "users.roomList", ignore = true)
    MessageDto toModel(Message message);

    Message toEntity(MessageDto messageDto);

     List<MessageDto> toModelList(List<Message> messageList);


}
