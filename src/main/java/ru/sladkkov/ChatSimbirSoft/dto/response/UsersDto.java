package ru.sladkkov.ChatSimbirSoft.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsersDto {

    private long userId;

    private String userName;

    private String userLogin;

    private String userPassword;

    private boolean active;

}
