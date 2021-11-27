package ru.sladkkov.ChatSimbirSoft.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sladkkov.ChatSimbirSoft.domain.Role;
import ru.sladkkov.ChatSimbirSoft.domain.Status;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsersDto {

    private long userId;

    private String userName;

    private String userLogin;

    private String userPassword;

    private Role role;

    private Status status;

}
