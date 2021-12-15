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
@Table(name = "room")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;

    @Column(name =  "owner_id")
    private Long ownerId;

    @Column(name =  "room_name")
    private String roomName;

    @Column(name = "type_room")
    private  String roomType;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "room")
    private List<Message> messageList;

    public Room(Long ownerId, String roomName, String roomType) {
        this.ownerId = ownerId;
        this.roomName = roomName;
        this.roomType = roomType;
    }
}

