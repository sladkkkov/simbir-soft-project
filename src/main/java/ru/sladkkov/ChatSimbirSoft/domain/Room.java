package ru.sladkkov.ChatSimbirSoft.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int roomId;

    private int owner_id;

    private String room_name;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "room")
    private List<Message> messageList;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "room")
    private RoomType roomType;


}

