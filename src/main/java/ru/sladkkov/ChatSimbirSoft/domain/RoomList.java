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
@Table(name = "room_list")
public class RoomList {
    @Id
    private Long userId;

    @Column(name = "ban_time")
    private Timestamp banTime;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "roles")
    private Roles roles;



    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId
    @JoinColumn(name = "user_id")
    private Users users;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId
    @JoinColumn(name = "room_id")
    private Room room;


}
