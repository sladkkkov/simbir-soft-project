package ru.sladkkov.ChatSimbirSoft.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.sladkkov.ChatSimbirSoft.domain.Message;
import ru.sladkkov.ChatSimbirSoft.dto.response.MessageDto;

import java.util.List;
@Mapper
public interface MessageMapper {

    MessageMapper INSTANCE = Mappers.getMapper(MessageMapper.class);

    MessageDto toModel(Message message);

    Message toEntity(MessageDto messageDto);

    List<MessageDto> toModelList(List<Message> messageList);
}
