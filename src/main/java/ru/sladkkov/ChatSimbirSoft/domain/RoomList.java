package ru.sladkkov.ChatSimbirSoft.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class RoomList {
    @Id
    private Long userId;
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
