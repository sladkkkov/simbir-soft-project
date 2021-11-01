package ru.sladkkov.ChatSimbirSoft.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    private String userLogin;

    private String userName;

    private String userPassword;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "users")
    private List<Message> messageList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "users")
    private List<RoomList> roomLists;
}
