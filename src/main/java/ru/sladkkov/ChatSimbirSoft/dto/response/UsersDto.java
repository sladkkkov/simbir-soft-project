package ru.sladkkov.ChatSimbirSoft.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sladkkov.ChatSimbirSoft.domain.Users;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsersDto {
    private long userId;
    private String userName;
    private  String userLogin;
    private  String userPassword;

    public Users toUser() {
        Users user = new Users();
        user.setUserId(userId);
        user.setUserName(userName);
        user.setUserLogin(userLogin);
        user.setUserPassword(userPassword);
        return user;
    }

    public static UsersDto fromUser(Users users) {
        UsersDto usersDto = new UsersDto();
        usersDto.setUserId(users.getUserId());
        usersDto.setUserName(users.getUserName());
        usersDto.setUserLogin(users.getUserLogin());
        usersDto.setUserPassword(users.getUserPassword());
        return usersDto;
    }

}
