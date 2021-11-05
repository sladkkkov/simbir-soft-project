package ru.sladkkov.ChatSimbirSoft.dto.request;

import lombok.Data;
import ru.sladkkov.ChatSimbirSoft.domain.Users;

@Data
public class UsersRequestDto {
    private long userId;

    public Users toUser() {
        Users user = new Users();
        user.setUserId(userId);
        return user;
    }

    public static UsersRequestDto fromUser(Users users) {
        UsersRequestDto usersRequestDto = new UsersRequestDto();
        usersRequestDto.setUserId(users.getUserId());
        return usersRequestDto;
    }
}
