package ru.sladkkov.ChatSimbirSoft.dto.response;

import lombok.Data;
import ru.sladkkov.ChatSimbirSoft.domain.Users;

@Data
public class UsersDto {
    private long userId;
    private String userName;

    public Users ToUser() {
        Users user = new Users();
        user.setUserId(userId);
        user.setUserName(userName);
        return user;
    }

    public static UsersDto fromUser(Users users) {
        UsersDto usersDto = new UsersDto();
        usersDto.setUserId(users.getUserId());
        usersDto.setUserName(users.getUserName());
        return usersDto;
    }

}
