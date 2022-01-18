package ru.sladkkov.ChatSimbirSoft.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.sladkkov.ChatSimbirSoft.domain.RoomList;
import ru.sladkkov.ChatSimbirSoft.dto.response.RoomListDto;

import java.util.List;

@Mapper
public interface RoomListMapper {

    RoomListMapper INSTANCE = Mappers.getMapper(RoomListMapper.class);


    @Mapping(target = "room.messageList", ignore = true)
    RoomListDto toModel(RoomList roomList);

    RoomList toEntity(RoomListDto roomListDto);

    List<RoomListDto> toModelList(List<RoomList> roomListList);


}
