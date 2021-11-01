package ru.sladkkov.ChatSimbirSoft.dto.request;

import lombok.Data;
import ru.sladkkov.ChatSimbirSoft.domain.Users;
import ru.sladkkov.ChatSimbirSoft.dto.response.UsersDto;

@Data
public class UsersRequestDto {
    private int userId;

    public Users ToUser() {
        Users user = new Users();
        user.setUserId(userId);
        return user;
    }

    public static UsersDto fromUser(Users users) {
        UsersDto usersDto = new UsersDto();
        usersDto.setUserId(users.getUserId());
        return usersDto;
    }
}
