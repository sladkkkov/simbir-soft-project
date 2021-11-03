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
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;

    private Long owner_id;

    private String room_name;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "room")
    private List<Message> messageList;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "room")
    private RoomType roomType;


}

