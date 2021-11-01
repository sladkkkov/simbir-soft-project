package ru.sladkkov.ChatSimbirSoft.dto.request;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Data
public class UsersCreateDto {
    private int userId;
    private String userName;
    private String userLogin;
    private String userPassword;
}
