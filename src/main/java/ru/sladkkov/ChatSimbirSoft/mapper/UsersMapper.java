package ru.sladkkov.ChatSimbirSoft.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.sladkkov.ChatSimbirSoft.domain.Role;
import ru.sladkkov.ChatSimbirSoft.domain.Status;
import ru.sladkkov.ChatSimbirSoft.domain.Users;
import ru.sladkkov.ChatSimbirSoft.dto.request.UsersRequestDto;
import ru.sladkkov.ChatSimbirSoft.dto.response.UsersDto;

import java.util.List;

@Mapper(uses = {Role.class, Status.class})
public interface UsersMapper {

    UsersMapper userInstance = Mappers.getMapper(UsersMapper.class);

    UsersRequestDto toModel(Users users);

    Users toEntity(UsersDto userDto);

     List<UsersRequestDto> toModelList(List<Users> users);
}
