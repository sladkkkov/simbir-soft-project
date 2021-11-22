package ru.sladkkov.ChatSimbirSoft.dto.request;

import lombok.Data;

@Data
public class AuthenticationRequestDto {
    private String login;
    private String password;

}
