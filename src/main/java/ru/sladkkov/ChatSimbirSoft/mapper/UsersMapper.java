package ru.sladkkov.ChatSimbirSoft.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.sladkkov.ChatSimbirSoft.domain.Role;
import ru.sladkkov.ChatSimbirSoft.domain.Status;
import ru.sladkkov.ChatSimbirSoft.domain.Users;
import ru.sladkkov.ChatSimbirSoft.dto.response.UsersDto;

import java.util.List;

@Mapper(uses = {Role.class, Status.class})
public interface UsersMapper {

    UsersMapper INSTANCE = Mappers.getMapper(UsersMapper.class);

    UsersDto toModel(Users users);

    Users toEntity(UsersDto userDto);

     List<UsersDto> toModelList(List<Users> users);
}
