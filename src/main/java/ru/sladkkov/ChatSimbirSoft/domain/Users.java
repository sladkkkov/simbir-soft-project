package ru.sladkkov.ChatSimbirSoft.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String userLogin;

    private String userName;

    private String userPassword;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "users")
    private List<Message> messageList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "users")
    private List<RoomList> roomLists;
}
