package ru.sladkkov.ChatSimbirSoft.service.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.sladkkov.ChatSimbirSoft.domain.Room;
import ru.sladkkov.ChatSimbirSoft.dto.response.RoomDto;

import java.util.List;

@Mapper
public interface RoomMapper {

    RoomMapper INSTANCE = Mappers.getMapper(RoomMapper.class);

    RoomDto toModel(Room room);

    Room toEntity(RoomDto roomDto);

    List<RoomDto> toModelList(List<Room> room);
}
