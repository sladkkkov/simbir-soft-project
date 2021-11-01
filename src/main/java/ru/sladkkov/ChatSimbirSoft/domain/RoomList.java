package ru.sladkkov.ChatSimbirSoft.domain;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
public class RoomList {
    @Id
    private int userId;
    private Timestamp ban_time;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "roles")
    private Roles roles;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId
    @JoinColumn(name = "userId")
    private Users users;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId
    @JoinColumn(name = "room_id")
    private Room room;


}
