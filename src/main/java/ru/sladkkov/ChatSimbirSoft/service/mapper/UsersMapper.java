package ru.sladkkov.ChatSimbirSoft.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.sladkkov.ChatSimbirSoft.domain.Users;
import ru.sladkkov.ChatSimbirSoft.dto.response.UsersDto;

import java.util.List;
@Mapper
public interface UsersMapper {

    UsersMapper INSTANCE = Mappers.getMapper(UsersMapper.class);

    UsersDto toModel(Users users);

    Users toEntity(UsersDto userDto);

    List<UsersDto> toModelList(List<Users> users);
}
